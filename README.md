# Expense Sharing App (Split-wise Clone)

## Project Overview

**Expense Sharing App** is a Spring Boot application that allows users to create groups, add expenses, and settle balances among members. This project includes:

* **Google OAuth2 Login** for authentication
* **Role-based access** (USER and ADMIN)
* **JWT** for stateless API authentication
* **Transactional expense management**
* **H2 in-memory database** for database

---

## Table of Contents

1. [Tech Stack](#tech-stack)
2. [Architecture & Flow](#architecture--flow)
3. [Entities](#entities)
4. [API Endpoints](#api-endpoints)
5. [Authentication & Authorization](#authentication--authorization)
6. [JWT & Roles](#jwt--roles)
7. [Setup & Run](#setup--run)

---

## Tech Stack

* Java 21 / Spring Boot 3.x
* Spring Security (OAuth2 + JWT)
* H2 Database (In-memory)
* Lombok for boilerplate reduction
* Maven for dependency management

---

## Architecture & Flow

**Request Flow**:

```
User → Protected API → Spring Security → Unauthenticated → /api/auth/login
→ Redirect → Google OAuth2 Login → Callback → CustomOAuth2UserService
→ User saved/updated in DB → OAuth2LoginSuccessHandler
→ JWT generated → Client stores token → Subsequent API calls
→ JwtAuthFilter validates token → Controller processes request
```

**Key Features**:

* OAuth2 login via Google
* Automatic user creation in DB
* Dynamic role assignment (`ROLE_USER` / `ROLE_ADMIN`)
* JWT-based stateless authentication
* Transactional handling of expenses and settlements

---

## Entities

### 1. User

* `id` (Long)
* `name` (String)
* `email` (String, unique)
* `phone` (String)
* `role` (String, e.g., USER/ADMIN)

### 2. Group

* `id` (Long)
* `name` (String)
* `description` (String)
* `members` (List<User>)

### 3. Expense

* `id` (Long)
* `title` (String)
* `amount` (Double)
* `paidBy` (User)
* `group` (Group)
* `splits` (Map<User, Double>) → calculated dynamically

### 4. Balance

* Tracks **net balances** per user per group (optional, can be calculated from expenses dynamically)

---

## API Endpoints

Refer API Documentation on http://localhost:8080/swagger-ui/index.html#/

> JWT must be included in `Authorization: Bearer <token>` for all protected endpoints.

---

## Authentication & Authorization

* **Google OAuth2** login is used for authentication.
* User info is automatically stored/updated in H2 DB.
* **Dynamic role assignment**:

```java
if(adminEmails.contains(email)) {
    role = "ROLE_ADMIN";
} else {
    role = "ROLE_USER";
}
```

* Roles are used for **role-based access control** on endpoints.

---

## JWT & Roles

* JWT includes username, email, roles, issuedAt & expiryTime
* JWT is stateless; no session storage required
* `JwtAuthFilter` validates token and sets `SecurityContext` for controller access

---

## Setup & Run

1. Clone repository:

```bash
git clone <repo-url>
cd expense-sharing-app
```

2. Configure Google OAuth2 in `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: <YOUR_CLIENT_ID>
            client-secret: <YOUR_CLIENT_SECRET>
            scope:
              - profile
              - email
server:
  port: 8080
```

3. Run Spring Boot app:

```bash
mvn spring-boot:run
```

4. Access H2 console: `http://localhost:8080/h2-console`
   JDBC URL: `jdbc:h2:mem:testdb`

5. Start login flow: `http://localhost:8080/api/auth/login`


---

### Notes / Tips

* **CustomOAuth2UserService** handles user creation & role assignment.
* **OAuth2LoginSuccessHandler** generates JWT after successful login.


---

