# EcoMentor Backend

EcoMentor is a backend service designed to support environmental education and sustainability initiatives.

## Features

- User authentication and authorization
- Achievement tracking system
- Geospatial data support
- Email notifications
- API documentation with Swagger/OpenAPI
- MongoDB integration for chat functionality

## Technologies

- Java 21
- Spring Boot 3.4
- Spring Security
- Spring Data JPA
- PostgreSQL with PostGIS extension
- MongoDB
- Docker
- JUnit 5 & Mockito for testing
- Checkstyle for code quality

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose (for containerized development)
- PostgreSQL with PostGIS extension (if running locally)
- MongoDB (if running locally)

## Environment Setup

The application requires the following environment variables:
- `GEMINI_API_KEY`: API key for Google's Gemini AI service
- `SPRING_MAIL_PASSWORD`: Password for the email service
- `DB_HOST`: PostgreSQL host (defaults to "postgres" in Docker)
- `MONGO_HOST`: MongoDB host (defaults to "mongo" in Docker)

## Quick Start

### Running with Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/yourusername/EcoMentor-backend.git
cd EcoMentor-backend

# Start the application with Docker Compose
docker-compose up
```

### Running Locally with Maven

```bash
# Clone the repository
git clone https://github.com/yourusername/EcoMentor-backend.git
cd EcoMentor-backend

# Build and run the application
./mvnw spring-boot:run
```

Alternatively, use the provided script:
```bash
./run-dev.sh
```

## API Documentation

Once the application is running, you can access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Testing

Run all tests:
```bash
./mvnw test
```

Run specific tests:
```bash
./mvnw test -Dtest=TestClassName
```

Run tests with coverage:
```bash
./mvnw test jacoco:report
```

## Development Guidelines

For detailed development guidelines, including:
- Architecture overview
- Code style and naming conventions
- Testing strategies
- Database migrations
- Error handling

Please refer to the [Development Guidelines](guidelines.md).

## Building for Production

Build the JAR:
```bash
./mvnw clean package
```

Build the Docker image:
```bash
docker build -t ecomentor-backend .
```

## License

[Add license information here]

