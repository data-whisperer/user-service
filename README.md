# User Service
Manages user data, authentication, and resume management

## Configuration

### Database Configuration
The application uses PostgreSQL. Configure the database connection in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/userservice
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### File Storage Configuration
The application stores uploaded resumes in a configurable directory. Configure the upload directory in `application.properties`:

```properties
# For development
