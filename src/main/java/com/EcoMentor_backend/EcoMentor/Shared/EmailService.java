package com.EcoMentor_backend.EcoMentor.Shared;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Environment env;

    private final JavaMailSender javaMailSender;


    private final ResourceLoader resourceLoader;

    public EmailService(JavaMailSender javaMailSender, Environment env,
                        @Qualifier("webApplicationContext") ResourceLoader resourceLoader) {
        this.javaMailSender = javaMailSender;
        this.env = env;
        this.resourceLoader = resourceLoader;
    }

    public String loadHtmlTemplate(String path) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContentPath)
            throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(loadHtmlTemplate(htmlContentPath), true); // `true` enables HTML

        ClassPathResource logoImage = new ClassPathResource("email/logo.png");
        helper.addInline("logoImage", logoImage);

        javaMailSender.send(message);
    }

}
