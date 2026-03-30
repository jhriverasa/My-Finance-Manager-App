# MyFinanceManager API

Backend API for MyFinanceManager built with Spring Boot 3.x and Java 21, following Hexagonal Architecture principles.

## Tech Stack

- **Java 21** - Modern Java with Records, Virtual Threads, Pattern Matching
- **Spring Boot 3.x** - Latest Spring Boot framework
- **Gradle** - Build tool
- **PostgreSQL 16** - Database
- **Spring Security + JWT** - Authentication & Authorization

## Project Structure

```
src/main/java/com/myfinancemanager/api/
├── domain/                    # Domain layer (core business logic)
│   ├── model/                 # Domain entities (POJOs)
│   ├── port/                  # Ports (interfaces)
│   │   ├── in/                # Input ports (use cases)
│   │   └── out/               # Output ports (repositories)
│   └── service/               # Domain services
├── application/               # Application layer
│   ├── dto/                   # Data Transfer Objects
│   ├── usecase/               # Use case implementations
│   └── exception/             # Application exceptions
└── infrastructure/            # Infrastructure layer
    ├── adapter/               # Adapters (controllers, persistence)
    │   ├── in/                # Input adapters (REST controllers)
    │   └── out/               # Output adapters (JPA, external APIs)
    ├── config/                # Configuration classes
    ├── persistence/           # JPA entities, repositories
    ├── security/              # JWT, Spring Security
    └── exception/             # Global exception handling
```

## Getting Started

### Prerequisites

- Java 21+
- Gradle 8.x
- PostgreSQL 16 (or Docker)

### Database Setup

1. Start the database using Docker Compose (from project root):
   ```bash
   cd infrastructure
   docker-compose up -d
   ```

2. Or connect to your own PostgreSQL instance and update `application.yml`.

### Running the Application

1. Copy environment file:
   ```bash
   cp .env.example .env
   ```

2. Update `.env` with your database credentials.

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. The API will be available at `http://localhost:8080`

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Login user |
| POST | `/api/v1/auth/refresh` | Refresh access token |
| GET | `/api/v1/auth/validate?token=xxx` | Validate JWT token |
| GET | `/actuator/health` | Health check |

## Quality Tools

### Code Quality

- **Checkstyle** - Code style checking
  ```bash
  ./gradlew checkstyleMain
  ```

- **Spotless** - Code formatting
  ```bash
  ./gradlew spotlessApply
  ```

- **SpotBugs** - Bug detection
  ```bash
  ./gradlew spotbugsMain
  ```

### Testing

- **Unit Tests**
  ```bash
  ./gradlew test
  ```

- **Coverage Report**
  ```bash
  ./gradlew jacocoTestReport
  ```

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Server port | `8080` |
| `DB_URL` | Database JDBC URL | `jdbc:postgresql://localhost:5432/mfm_db` |
| `DB_USERNAME` | Database username | `mfm_user` |
| `DB_PASSWORD` | Database password | `mfm_password` |
| `JWT_SECRET` | JWT signing key | (development key) |
| `JWT_EXPIRATION` | Token expiration (ms) | `86400000` (24h) |

### Profiles

- `dev` - Development profile with debug logging
- `prod` - Production profile (to be implemented)

## Docker

### Build Docker Image

```bash
./gradlew bootJar
docker build -t myfinance-manager-api .
```

### Run with Docker

```bash
docker run -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/mfm_db \
  myfinance-manager-api
```

## API Documentation

API documentation is available via OpenAPI/Swagger (to be implemented).

## License

Private - All rights reserved