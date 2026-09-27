# Student ERP

A small Student ERP web application for an academic project. The project is intentionally simple and readable. It uses REST APIs, MySQL, Spring Data JPA, Spring Boot Actuator, and a basic HTML/CSS/JavaScript frontend.

Azure Application Insights and OpenTelemetry are not included yet. They can be added later after the basic application is working.

## Technology Stack

- Java 17
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- MySQL
- Spring Boot Actuator
- HTML/CSS/JavaScript

## Project Structure

```text
src/main/java/com/example/studenterp/
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

The application follows a simple layered architecture:

```text
Controller -> Service -> Repository -> MySQL
```

## Database Setup

Create a MySQL database:

```sql
CREATE DATABASE student_erp;
```

Set database configuration using environment variables:

```text
DB_URL=jdbc:mysql://localhost:3306/student_erp
DB_USERNAME=root
DB_PASSWORD=your_password
```

The application uses `schema.sql` and `data.sql` to create tables and insert sample records.

Sample login:

```text
username: ananya
password: pass123
```

## Configuration

Main configuration file:

```text
src/main/resources/application.properties
```

Important properties:

```properties
app.announcements.url=${ANNOUNCEMENTS_API_URL:https://jsonplaceholder.typicode.com/posts?_limit=5}
app.test.slow-delay-ms=${SLOW_DELAY_MS:3000}
app.marks.db-delay-ms=${MARKS_DB_DELAY_MS:3000}
```

## Run the Application

```bash
mvn spring-boot:run
```

Open the frontend:

```text
http://localhost:8080
```

Frontend pages:

```text
http://localhost:8080/dashboard.html
http://localhost:8080/profile.html
http://localhost:8080/marks.html
http://localhost:8080/attendance.html
http://localhost:8080/announcements.html
```

## API Endpoints

### Login

```http
POST /api/auth/login
```

Request:

```json
{
  "username": "ananya",
  "password": "pass123"
}
```

### Student Profile

```http
GET /api/students/1
```

### Student Marks

```http
GET /api/students/1/marks
```

This endpoint intentionally runs a configurable MySQL delay query before returning marks. It is used to demonstrate a slow database dependency later during monitoring.

### Student Attendance

```http
GET /api/students/1/attendance
```

### Announcements

```http
GET /api/announcements
```

This endpoint calls the external URL configured in `app.announcements.url`.

### Slow Test Endpoint

```http
GET /api/test/slow
```

Returns after a configurable delay.

### Error Test Endpoint

```http
GET /api/test/error
```

Throws a controlled exception handled by the global exception handler.

### Actuator

```http
GET /actuator/health
GET /actuator/info
```

## Sample Test Requests

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"ananya\",\"password\":\"pass123\"}"
```

```bash
curl http://localhost:8080/api/students/1
curl http://localhost:8080/api/students/1/marks
curl http://localhost:8080/api/students/1/attendance
curl http://localhost:8080/api/announcements
curl http://localhost:8080/api/test/slow
curl http://localhost:8080/api/test/error
```
