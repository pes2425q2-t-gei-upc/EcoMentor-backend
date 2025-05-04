FROM openjdk:21-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .


RUN apt-get update && apt-get install -y dos2unix && \
    dos2unix ./mvnw && \
    rm -rf /var/lib/apt/lists/*

RUN chmod +x ./mvnw

COPY src ./src

RUN ./mvnw clean package -DskipTests

RUN ls -l target/

ENTRYPOINT ["sh", "-c", "java -jar $(find target -name '*.jar' | head -n 1)"]