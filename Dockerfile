FROM openjdk:21-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw


COPY src ./src

RUN ./mvnw clean package -DskipTests


RUN ls -l target/

# Search for  ENTRYPOINT since jar wasnt working
ENTRYPOINT ["sh", "-c", "java -jar $(find target -name '*.jar' | head -n 1)"]