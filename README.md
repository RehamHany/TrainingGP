📝 Task Management Application

🎓 Graduation Project – Spring Boot Camp Training

A scalable and secure task management system developed using Java and Spring Boot, designed to help users manage tasks efficiently.

✨ Features

✅ Task Management: Create, update, delete, and track tasks.

🔒 Secure APIs: RESTful APIs secured with Spring Security.

🗄️ Database: Persistent storage using MySQL.

⚡ Microservices Architecture: Built for scalability and maintainability.

📄 API Documentation: Clear specifications using Swagger/OpenAPI.

✉️ Email Notifications: Users receive notifications for task updates via JavaMail.

🚨 Error Handling: Global exception handling for system stability.

🧪 Testing: Unit tests to ensure reliable functionality.

🛠️ Technologies Used

Backend: Java, Spring Boot, Spring Data JPA, Spring Security

Database: MySQL

API Documentation: Swagger/OpenAPI

Email Service: JavaMail

Testing: JUnit

⚙️ Installation & Setup

Clone the repository:

git clone <repository_url>


Go to project folder:

cd task-management-application


Configure MySQL in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
spring.datasource.username=root
spring.datasource.password=your_password


Run the application:

mvn spring-boot:run


Swagger UI:
Access API docs at → http://localhost:8080/swagger-ui/index.html

🚀 Usage

Register a user and get a token for authentication.

Use the token to manage tasks (create, update, delete, view).

Receive email notifications for task changes.

🔮 Future Improvements

🌐 Add frontend interface for a complete web application.

📊 Advanced reporting and analytics for tasks.

📱 Support push notifications in addition to emails.
