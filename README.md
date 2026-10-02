# Perfume Webstore

A full-stack e-commerce application for selling perfumes, built with Spring Boot on the backend and React.js on the frontend.

This project includes authentication, product management, shopping cart logic, order processing, and a role-based admin dashboard.

## Tech Stack

### Backend
- Java 8+
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT authentication
- REST API
- GraphQL API
- JUnit and Mockito for testing

### Frontend
- React.js
- TypeScript
- Redux Toolkit
- Ant Design
- npm / yarn

### Infrastructure
- AWS S3
- Heroku
- Maven

## Features

- JWT-based authentication and email verification
- Login via Google, Facebook, or GitHub
- Product search and filtering
- Add/remove products from the shopping cart
- Place and manage orders
- Change password and view order history
- Admin dashboard to manage products and users
- Admin ability to view all customer orders

## Project Structure

- `backend/` — Spring Boot server and business logic
- `frontend/` — React frontend application
- `README.md` — project overview and startup instructions

## Prerequisites

Before running the application, install:

1. Java 8+
2. Maven
3. Node.js and npm or yarn
4. PostgreSQL
5. IntelliJ IDEA (recommended)

## Database Setup

1. Create a PostgreSQL database named `perfume`
2. Create a second database named `perfumetest`
3. Update your database configuration in the backend `application.properties` file
4. Enable the required PostgreSQL JDBC settings for your environment

## Backend Setup

1. Open the backend project in IntelliJ IDEA
2. Run the main Spring Boot application class
3. Start the server on port `8080`

## Frontend Setup

From the `frontend` directory:

```bash
npm install
npm start
```

Then open:

```text
http://localhost:3000
```

## Swagger Documentation

Local Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Default Login

The project includes a demo admin account:

- Email: `admin@gmail.com`
- Password: `admin`

## Notes

This project was originally based on an existing ecommerce template and has been adapted for personal learning and development use. It is suitable for exploring full-stack Java and React architecture, authentication, and deployment workflows.

## Run Summary

```bash
# backend
mvn spring-boot:run

# frontend
cd frontend
npm install
npm start
```

## License

This project is licensed under the MIT License.
