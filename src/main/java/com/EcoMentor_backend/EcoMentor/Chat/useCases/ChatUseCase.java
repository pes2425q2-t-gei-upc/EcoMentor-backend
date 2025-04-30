package com.EcoMentor_backend.EcoMentor.Chat.useCases;

import com.EcoMentor_backend.EcoMentor.Chat.entity.Chat;
import com.EcoMentor_backend.EcoMentor.Chat.infraestructure.repositories.ChatRepository;
import com.EcoMentor_backend.EcoMentor.Chat.useCases.dto.ChatResponseDTO;
import com.EcoMentor_backend.EcoMentor.User.infrastructure.repositories.UserRepository;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ChatUseCase {
    private final GeminiService gemini;
    private final ChatRepository repo;
    private final UserRepository userRepository;

    public ChatUseCase(GeminiService gemini, ChatRepository repo, UserRepository userRepository) {
        this.gemini = gemini;
        this.repo = repo;
        this.userRepository = userRepository;
    }

    public ChatResponseDTO execute(String message, Long userId, String chatName) {
        Date nowUtc = new Date();
        Date now = new Date(nowUtc.getTime() + Duration.ofHours(2).toMillis());
        long twoHoursInMs = 2L * 60 * 60 * 1000;
        boolean suspicus = false;

        if (inappropriateLanguageDetector(message)) {
            Chat chat = Chat.builder()
                    .userId(userId)
                    .message("")
                    .response("")
                    .timestamp(now)
                    .chatName(chatName)
                    .isSuspicious(true)
                    .build();

            repo.save(chat);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inappropriate language detected");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<Chat> history = repo.findByUserIdAndChatNameOrderByTimestampAsc(userId, chatName);

        if (history.size() >= 3) {
            Chat m1 = history.get(history.size() - 3);
            Chat m2 = history.get(history.size() - 2);
            Chat m3 = history.get(history.size() - 1);
            if (m3.isSuspicious()) {
                Date messageTime = m3.getTimestamp();
                long secondsDiff = (now.getTime() - messageTime.getTime()) / 1000;
                if (secondsDiff < 300) {
                    throw new ResponseStatusException(
                            HttpStatus.TOO_MANY_REQUESTS,
                            "Messages are too close in time"
                    );
                }
            } else {
                Date t1 = m1.getTimestamp();
                Date t2 = m2.getTimestamp();
                Date t3 = m3.getTimestamp();

                long interval1 = (t2.getTime() - t1.getTime()) / 1000;
                long interval2 = (t3.getTime() - t2.getTime()) / 1000;

                if (interval1 < 40 && interval2 < 40) {
                    suspicus = true;
                }
            }
        }

        List<Map<String, Object>> contents = new ArrayList<>();
        for (Chat c : history) {
            String msg = c.getMessage();
            String res = c.getResponse();

            if (msg != null && !msg.isBlank() && res != null && !res.isBlank()) {
                contents.add(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", msg))
                ));

                contents.add(Map.of(
                        "role", "model",
                        "parts", List.of(Map.of("text", res))
                ));
            }
        }
        contents.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", message))
        ));

        String answer = gemini.generateContent(contents);

        if (answer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating response");
        }



        Chat chat = Chat.builder()
                .userId(userId)
                .message(message)
                .response(answer)
                .timestamp(now)
                .chatName(chatName)
                .isSuspicious(suspicus)
                .build();

        repo.save(chat);

        return ChatResponseDTO.builder()
                .message(message)
                .response(answer)
                .timestamp(now)
                .isSuspicious(suspicus)
                .build();
    }

    boolean inappropriateLanguageDetector(String sentence) {
        if (sentence == null) {
            return false;
        }

        // 1) Carga del fichero badwords.properties
        ResourceBundle bundle = ResourceBundle.getBundle("badwords");

        // 2) Reúne todas las palabras prohibidas en un Set (para evitar duplicados)
        Set<String> badWords = new HashSet<>();
        for (String key : bundle.keySet()) {
            badWords.add(bundle.getString(key).toLowerCase());
        }

        String patternSrc = "\\b("
              +  badWords.stream()
                        .map(Pattern::quote)
                        .collect(Collectors.joining("|"))
                + ")\\b";

        Pattern pattern = Pattern.compile(patternSrc, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sentence);
        return matcher.find();
    }

}
