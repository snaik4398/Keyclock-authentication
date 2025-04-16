# Keycloak Authentication Service

This is a Spring Boot microservice that provides a wrapper around Keycloak authentication, handling user authentication and JWT token management.

## Prerequisites

- Java 11 or higher
- Maven
- Keycloak Server (version 19.0.1)

## Setup

1. Install and Run Keycloak Server:
   ```bash
   # Download Keycloak 19.0.1
   # Start Keycloak
   ./bin/standalone.sh
   ```

2. Configure Keycloak:
   - Create a new realm (e.g., "your-realm")
   - Create a new client (e.g., "your-client")
   - Create users and roles as needed

3. Update application.yml:
   ```yaml
   keycloak:
     auth-server-url: http://localhost:8080/auth
     realm: your-realm
     resource: your-client
   ```

4. Build and Run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### 1. Login
```http
POST /auth/login
Content-Type: application/json

{
    "username": "your-username",
    "password": "your-password"
}
```

### 2. Validate Token
```http
GET /auth/validate
Authorization: Bearer your-jwt-token
```

## Security

- The service uses Spring Security with Keycloak adapter
- All endpoints except /auth/** require authentication
- JWT tokens are validated against Keycloak server

## Configuration

The service runs on port 8081 by default. You can change this in the application.yml file.

## Error Handling

The service includes basic error handling for:
- Invalid credentials
- Invalid tokens
- Server errors

## Dependencies

- Spring Boot 2.7.3
- Keycloak 19.0.1
- Spring Security
- Lombok