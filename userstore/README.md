# User Store API

A Spring Boot backend API that replicates and extends the behavior
of [JSONPlaceholder](https://jsonplaceholder.typicode.com) with full REST operations, JWT-based authentication,
structured user data storage, and containerized deployment.

## Features

- **Full CRUD Operations**: Complete REST API following JSONPlaceholder structure
- **PostgreSQL Database**: Robust data storage with proper relationships
- **Database Migrations**: Flyway for schema management and data seeding
- **Input Validation**: Bean Validation for request validation
- **Exception Handling**: Global exception handler with structured error responses
- **Containerized**: Docker and Docker Compose for easy deployment
- **Health Checks**: Spring Boot Actuator for monitoring
- **Extended Functionality**: Search by name, city, username, and email

## Tech Stack

- **Java 17** with Spring Boot 3.2.5
- **PostgreSQL 16** for database
- **Spring Data JPA** for data access
- **Flyway** for database migrations
- **Docker** for containerization
- **Maven** for build management
- **Lombok** for reducing boilerplate code

## Quick Start

### Prerequisites

- Java 17 or later
- Maven 3.6+
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL (for local development)

### Local Development

1. **Clone and navigate to the project:**
   ```bash
   cd userstore
   ```

2. **Set up PostgreSQL database:**
   ```bash
   # Create database
   createdb userstore
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API:**
    - API Base URL: http://localhost:8080
    - Health Check: http://localhost:8080/actuator/health

### Docker Deployment
Fast start from root directory of the project:

```bash
docker-compose up -d
```

1. **Build and run with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

2. **Access the application:**
    - API Base URL: http://localhost:8080
    - PostgreSQL: localhost:5432

3. **Stop the application:**
   ```bash
   docker-compose down
   ```

## API Documentation

### Base URL

```
http://localhost:8080
```

### Endpoints

#### Get All Users

```http
GET /users
```

#### Get User by ID

```http
GET /users/{id}
```

#### Create User

```http
POST /users
Content-Type: application/json

{
  "name": "John Doe",
  "username": "johndoe",
  "email": "john@example.com",
  "phone": "123-456-7890",
  "website": "johndoe.com",
  "address": {
    "street": "123 Main St",
    "suite": "Apt 1",
    "city": "New York",
    "zipcode": "10001",
    "geo": {
      "lat": "40.7128",
      "lng": "-74.0060"
    }
  },
  "company": {
    "name": "Example Corp",
    "catchPhrase": "Innovative solutions",
    "bs": "harness real-time e-markets"
  }
}
```

#### Update User

```http
PUT /users/{id}
Content-Type: application/json

{
  // Same structure as POST
}
```

#### Partially Update User

```http
PATCH /users/{id}
Content-Type: application/json

{
  "name": "Updated Name"
}
```

#### Delete User

```http
DELETE /users/{id}
```

#### Extended Search Endpoints

```http
GET /users?name=John          # Search by name (contains)
GET /users?city=New York      # Search by city
GET /users?username=johndoe   # Search by username
GET /users?email=john@example.com  # Search by email
```

### Response Format

#### Success Response

```json
{
  "id": 1,
  "name": "Leanne Graham",
  "username": "Bret",
  "email": "Sincere@april.biz",
  "address": {
    "id": 1,
    "street": "Kulas Light",
    "suite": "Apt. 556",
    "city": "Gwenborough",
    "zipcode": "92998-3874",
    "geo": {
      "id": 1,
      "lat": "-37.3159",
      "lng": "81.1496"
    }
  },
  "phone": "1-770-736-8031 x56442",
  "website": "hildegard.org",
  "company": {
    "id": 1,
    "name": "Romaguera-Crona",
    "catchPhrase": "Multi-layered client-server neural-net",
    "bs": "harness real-time e-markets"
  }
}
```

#### Error Response

```json
{
  "timestamp": "2025-07-05T19:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Invalid input data",
  "details": {
    "email": "Email should be valid",
    "name": "Name is required"
  }
}
```

## Database Schema

The application uses a normalized database schema with the following tables:

- **users**: Main user information
- **address**: User addresses
- **geo**: Geographical coordinates
- **company**: Company information

Relationships:

- User → Address (One-to-One)
- User → Company (One-to-One)
- Address → Geo (One-to-One)

## Initial Data

The application comes pre-seeded with 10 users from the JSONPlaceholder API, including:

- Leanne Graham (Bret)
- Ervin Howell (Antonette)
- Clementine Bauch (Samantha)
- And 7 more users with complete data

## Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/userstore
spring.datasource.username=postgres
spring.datasource.password=password
# JPA
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
# Actuator
management.endpoints.web.exposure.include=health,info,metrics
```

### Docker Configuration

The Docker setup includes:

- PostgreSQL 16 container
- Spring Boot application container
- Health checks for both services
- Persistent volume for database data
- Network isolation

## Development

### Project Structure

```
src/
├── main/
│   ├── java/com/example/userstore/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access
│   │   ├── model/          # JPA entities
│   │   └── exception/      # Exception handling
│   └── resources/
│       ├── db/migration/   # Flyway migrations
│       └── application.properties
```

### Building

```bash
mvn clean package
```

### Testing

```bash
mvn test
```

## Monitoring

### Health Checks

- Application health: `GET /actuator/health`
- Database health: Included in application health check

### Metrics

- Application metrics: `GET /actuator/metrics`

## Contributing

1. Follow the existing code style and patterns
2. Add appropriate tests for new features
3. Update documentation as needed
4. Ensure all tests pass before submitting

## License

This project is for educational purposes and replicates the JSONPlaceholder API structure. 