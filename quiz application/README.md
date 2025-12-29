# Quiz Application

A full-stack quiz application built with Spring Boot backend and HTML/CSS/JavaScript frontend.

## Features

- User registration and login with JWT authentication
- Admin panel to create/update/delete quizzes and questions
- Take quizzes with timed questions
- Score calculation and leaderboard
- Categories and difficulty levels
- 20 sample Java programming questions included

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Setup and Running

1. **Start the Spring Boot application:**
   
   **Option 1: Using Maven (Recommended)**
   ```bash
   mvn spring-boot:run
   ```
   
   **Option 2: Using the batch file (Windows)**
   ```bash
   START_SERVER.bat
   ```
   
   **Option 3: Build and run the JAR**
   ```bash
   mvn clean package
   java -jar target/quiz-application-1.0.0.jar
   ```

2. **Wait for the server to start** - You should see a message like:
   ```
   Started QuizApplication in X.XXX seconds
   ```

3. **Open your browser and navigate to:**
   ```
   http://localhost:8090
   ```

**Note:** Make sure port 8090 is not being used by another application. If you see "Port already in use" error, either stop the other application or change the port in `application.properties`.

## Default Accounts

The application comes with pre-configured accounts:

- **Admin Account:**
  - Username: `admin`
  - Password: `admin123`
  - Access: Can create/edit/delete quizzes

- **Regular User Account:**
  - Username: `user`
  - Password: `user123`
  - Access: Can take quizzes and view results

## Database

The application uses H2 in-memory database. Data is automatically initialized on startup with:
- Admin and user accounts
- Sample Java Programming Quiz with 20 questions

To access H2 console: `http://localhost:8090/h2-console`
- JDBC URL: `jdbc:h2:mem:quizdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Authentication
- POST `/api/auth/signup` - Register new user
- POST `/api/auth/signin` - Login user

### User Endpoints (Requires Authentication)
- GET `/api/user/quizzes` - Get all available quizzes
- GET `/api/user/quizzes/{id}` - Get quiz by ID
- POST `/api/user/quizzes/{id}/attempt` - Submit quiz attempt
- GET `/api/user/attempts` - Get user's quiz attempts
- GET `/api/user/quizzes/{id}/leaderboard` - Get leaderboard for quiz

### Admin Endpoints (Requires Admin Role)
- GET `/api/admin/quizzes` - Get all quizzes
- GET `/api/admin/quizzes/{id}` - Get quiz by ID
- POST `/api/admin/quizzes` - Create new quiz
- PUT `/api/admin/quizzes/{id}` - Update quiz
- DELETE `/api/admin/quizzes/{id}` - Delete quiz

## Project Structure

```
src/
├── main/
│   ├── java/com/quiz/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── model/           # Entity models
│   │   ├── payload/         # DTOs for requests/responses
│   │   ├── repository/      # JPA repositories
│   │   ├── security/        # Security configuration
│   │   └── service/         # Business logic services
│   └── resources/
│       ├── static/          # Frontend files (HTML, CSS, JS)
│       └── application.properties
└── pom.xml
```

## Troubleshooting

### "Failed to fetch" or "Cannot connect to server" Error

If you see this error, it means the backend server is not running. Follow these steps:

1. **Start the backend server:**
   ```bash
   mvn spring-boot:run
   ```
   **OR** double-click `START_SERVER.bat` (Windows)

2. **Verify the server started successfully:**
   - Look for: "Started QuizApplication in X.XXX seconds" in the console
   - If you see errors, check the console output

3. **Check the port:**
   - Default port is 8090
   - Make sure no other application is using this port
   - You can change it in `application.properties` if needed

4. **Test the connection:**
   - Open browser and go to: `http://localhost:8090`
   - You should see the home page
   - Or test the API directly: `http://localhost:8090/api/auth/signin` (POST)

5. **Browser console (F12):**
   - Check the Console tab for detailed error messages
   - Check the Network tab to see if requests are being made
   - Look for CORS or connection errors

6. **Common issues:**
   - **Port 8090 already in use**: Stop other applications or change the port
   - **Java version**: Make sure Java 17+ is installed (`java -version`)
   - **Maven not found**: Install Maven or use the built-in wrapper
   - **Firewall blocking**: Check if Windows Firewall is blocking the connection

### Database Issues

- The application uses H2 in-memory database
- Data persists while the application is running
- On restart, data is re-initialized with default accounts and sample quiz

## Development

To build the project:
```bash
mvn clean install
```

To run tests:
```bash
mvn test
```

