# EcoMentor Backend Development Guidelines

This document provides essential information for developers working on the EcoMentor backend project.

## Build and Configuration Instructions

### Local Development Setup

1. **Prerequisites**:
   - Java 21
   - Maven
   - Docker and Docker Compose (for containerized development)
   - PostgreSQL with PostGIS extension (if running locally)
   - MongoDB (if running locally)

2. **Environment Variables**:
   The application requires the following environment variables:
   - `GEMINI_API_KEY`: API key for Google's Gemini AI service
   - `SPRING_MAIL_PASSWORD`: Password for the email service
   - `DB_HOST`: PostgreSQL host (defaults to "postgres" in Docker)
   - `MONGO_HOST`: MongoDB host (defaults to "mongo" in Docker)

3. **Running Locally with Maven**:
   ```bash
   ./mvnw spring-boot:run
   ```

   Alternatively, use the provided script:
   ```bash
   ./run-dev.sh
   ```

4. **Running with Docker**:
   ```bash
   docker-compose up
   ```

   For local Docker development:
   ```bash
   ./run-dockerLocal.sh
   ```

### Building for Production

1. **Building the JAR**:
   ```bash
   ./mvnw clean package
   ```

2. **Building the Docker Image**:
   ```bash
   docker build -t ecomentor-backend .
   ```

## Testing Information

### Test Configuration

The project uses JUnit 5 for testing with Mockito for mocking dependencies. Tests are organized by component and follow a standard structure:

- `src/test/java/com/EcoMentor_backend/EcoMentor/[Component]/useCases/`: Tests for business logic
- `src/test/java/com/EcoMentor_backend/EcoMentor/[Component]/infraestructure/controllers/`: Tests for API endpoints

### Running Tests

1. **Running All Tests**:
   ```bash
   ./mvnw test
   ```

2. **Running Specific Tests**:
   ```bash
   ./mvnw test -Dtest=TestClassName
   ```

3. **Running Tests with Coverage**:
   ```bash
   ./mvnw test jacoco:report
   ```

### Adding New Tests

1. **Unit Test Structure**:
   - Place tests in the corresponding package under `src/test/java`
   - Use descriptive test method names following the pattern `testMethodName_Scenario_ExpectedResult`
   - Use `@BeforeEach` for setup code
   - Use appropriate assertions from JUnit 5

2. **Controller Test Example**:
   ```java
   @Test
   public void testEndpoint_Scenario_ExpectedResult() {
       // Arrange
       RequestDTO dto = new RequestDTO();
       // Set up DTO properties

       when(useCase.execute(any())).thenReturn(expectedResult);

       // Act
       ResponseEntity<ResponseType> response = controller.endpoint(dto);

       // Assert
       assertEquals(HttpStatus.EXPECTED_STATUS, response.getStatusCode());
       assertEquals(expectedValue, response.getBody());
       verify(useCase, times(1)).execute(any());
   }
   ```

3. **Use Case Test Example**:
   ```java
   @Test
   public void testExecute_Scenario_ExpectedResult() {
       // Arrange
       when(repository.findSomething()).thenReturn(mockData);

       // Act
       Result result = useCase.execute(input);

       // Assert
       assertNotNull(result);
       assertEquals(expectedValue, result.getValue());
       verify(repository, times(1)).findSomething();
   }
   ```

## Code Style and Development Guidelines

### Architecture

The project follows a Clean Architecture approach with the following layers:
- **Entity**: Domain models
- **Use Cases**: Business logic
- **Infrastructure**: Controllers, repositories, and external services

### Naming Conventions

- **Classes**: PascalCase (e.g., `UserRepository`)
- **Methods and Variables**: camelCase (e.g., `getUserById`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Packages**: lowercase (e.g., `com.ecomentor_backend.ecomentor.user`)

### Code Quality

The project uses Checkstyle for code quality checks. Run Checkstyle with:
```bash
./mvnw checkstyle:check
```

The configuration is in `checkstyle.xml`. Ensure your code complies with these rules before submitting.

### Database Migrations

The project uses Spring Data JPA for database access. When making schema changes:
1. Update the entity classes
2. Test the changes locally
3. Document the changes in the commit message

### API Documentation

The project uses SpringDoc OpenAPI for API documentation. Access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### Error Handling

Use Spring's `ResponseStatusException` for HTTP-specific errors:

```
throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
```

For business logic exceptions, create custom exception classes and handle them in the controllers.

## Example: Adding a New Feature

Here's an example of adding a new utility method and its test:

1. **Create the utility class**:
   ```java
   // src/main/java/com/EcoMentor_backend/EcoMentor/Shared/utils/StringUtils.java
   package com.EcoMentor_backend.EcoMentor.Shared.utils;

   public class StringUtils {
       public static boolean isPalindrome(String input) {
           if (input == null) {
               return false;
           }

           String cleanInput = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

           if (cleanInput.isEmpty()) {
               return false;
           }

           int left = 0;
           int right = cleanInput.length() - 1;

           while (left < right) {
               if (cleanInput.charAt(left) != cleanInput.charAt(right)) {
                   return false;
               }
               left++;
               right--;
           }

           return true;
       }
   }
   ```

2. **Create the test**:
   ```java
   // src/test/java/com/EcoMentor_backend/EcoMentor/Shared/utils/StringUtilsTest.java
   package com.EcoMentor_backend.EcoMentor.Shared.utils;

   import org.junit.jupiter.api.Test;
   import static org.junit.jupiter.api.Assertions.*;

   public class StringUtilsTest {
       @Test
       public void testIsPalindrome_WithValidPalindromes_ReturnsTrue() {
           assertTrue(StringUtils.isPalindrome("racecar"));
           assertTrue(StringUtils.isPalindrome("A man, a plan, a canal: Panama"));
       }

       @Test
       public void testIsPalindrome_WithNonPalindromes_ReturnsFalse() {
           assertFalse(StringUtils.isPalindrome("hello"));
           assertFalse(StringUtils.isPalindrome("world"));
       }

       @Test
       public void testIsPalindrome_WithEdgeCases_HandlesCorrectly() {
           assertFalse(StringUtils.isPalindrome(null));
           assertFalse(StringUtils.isPalindrome(""));
           assertTrue(StringUtils.isPalindrome("a"));
       }
   }
   ```

3. **Run the test**:
   ```bash
   ./mvnw test -Dtest=StringUtilsTest
   ```
