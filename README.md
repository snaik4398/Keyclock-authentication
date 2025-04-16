# Keycloak Authentication Service

A Spring Boot microservice that provides a wrapper around Keycloak authentication, handling user authentication and JWT token management.

## Prerequisites

- Java 11 or higher
- Maven
- Docker and Docker Compose
- Postman (for testing APIs)

## Quick Start with Docker

1. Clone the repository:
```bash
git clone https://github.com/snaik4398/Keyclock-authentication.git
cd Keyclock-authentication
```

2. Build the application:
```bash
mvn clean package -DskipTests
```

3. Start the services using Docker Compose:
```bash
docker-compose up -d
```

This will start:
- Keycloak server on port 8080
- Authentication service on port 8081

## Keycloak Initial Setup

1. Access Keycloak Admin Console:
   - URL: http://localhost:8080/auth/admin
   - Username: admin
   - Password: admin

2. Create a New Realm:
   - Click "Add realm"
   - Name it "your-realm"
   - Click "Create"

3. Create a New Client:
   - Go to "Clients" → "Create"
   - Client ID: your-client
   - Client Protocol: openid-connect
   - Click "Save"
   - Update client settings:
     - Access Type: public
     - Valid Redirect URIs: http://localhost:8081/*
     - Web Origins: *

4. Create a Test User:
   - Go to "Users" → "Add user"
   - Username: testuser
   - Email: testuser@example.com
   - Click "Save"
   - Go to "Credentials" tab
   - Set password: testpass
   - Disable "Temporary" toggle
   - Click "Set Password"

## API Documentation

### 1. User Login

**Request:**
```http
POST http://localhost:8081/auth/login
Content-Type: application/json

{
    "username": "testuser",
    "password": "testpass"
}
```

**Response:**
```json
{
    "access_token": "eyJhbGciOiJSUzI1...",
    "expires_in": 300,
    "refresh_expires_in": 1800,
    "refresh_token": "eyJhbGciOiJIUzI1...",
    "token_type": "bearer",
    "session_state": "1234567890"
}
```

### 2. Validate Token

**Request:**
```http
GET http://localhost:8081/auth/validate
Authorization: Bearer eyJhbGciOiJSUzI1...
```

**Response:**
```json
{
    "valid": true
}
```

## Testing with Postman

1. Import the Postman collection:
   - Open Postman
   - Click "Import"
   - Use the following curl commands:

```bash
# Login
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "testpass"}'

# Validate Token
curl -X GET http://localhost:8081/auth/validate \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Running Locally (Without Docker)

1. Start Keycloak server:
```bash
# Download Keycloak 19.0.1
# Start Keycloak
./bin/standalone.sh
```

2. Update application.yml:
```yaml
keycloak:
  auth-server-url: http://localhost:8080/auth
  realm: your-realm
  resource: your-client
```

3. Run the application:
```bash
mvn spring-boot:run
```

## Docker Commands

1. Build the image:
```bash
docker build -t keycloak-auth-service .
```

2. Start all services:
```bash
docker-compose up -d
```

3. View logs:
```bash
docker-compose logs -f
```

4. Stop all services:
```bash
docker-compose down
```

## Troubleshooting

1. If Keycloak is not accessible:
   - Check if the container is running: `docker ps`
   - View Keycloak logs: `docker-compose logs keycloak`

2. If authentication service fails:
   - Verify Keycloak configuration in application.yml
   - Check service logs: `docker-compose logs auth-service`

3. Common Issues:
   - Keycloak not starting: Ensure ports 8080 is free
   - Auth service not starting: Ensure port 8081 is free
   - Connection refused: Ensure both services are running

## Security Considerations

1. Production Setup:
   - Use HTTPS
   - Configure proper CORS settings
   - Use secure passwords
   - Enable rate limiting
   - Configure proper token expiration times

2. Environment Variables:
   - Never commit sensitive data
   - Use environment variables for credentials
   - Use secrets management in production

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.