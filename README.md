# 🧠 MatchHire Backend

**MatchHire** is a modern recruitment platform API built with **Spring Boot 3**, designed to connect candidates and companies efficiently through a **secure, scalable, and maintainable architecture**.

---

## 🚀 Overview

The backend provides the foundation for the MatchHire platform, including:

- 🔐 **JWT Authentication** with Spring Security 6
- 🔁 **Refresh Token Flow** using HttpOnly cookies and Redis 7.2
- 🪪 **Role-Based Access Control (RBAC)** for users (Admin, Company, Candidate)
- 🧾 **Structured logging** with unique `traceId` for every request
- ⚙️ **Clean architecture** with clear separation of layers (Controller → Service → Repository)
- 🧱 **CRUD operations** for Candidates, Companies, and Jobs
- 🗑️ **Soft delete** strategy (`active = false`) for logical data removal
- 🧩 **DTOs and Mappers** with MapStruct
- 🧪 **Unit tests** using JUnit 5
- 💡 **Validation and exception handling** with Jakarta Validation and custom error responses

---

## 🧩 Tech Stack

| Category | Technology |
|-----------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Build Tool | Maven |
| Database | PostgreSQL |
| ORM | Spring Data JPA |
| Object Mapping | MapStruct |
| Validation | Jakarta Validation |
| Security | Spring Security + JWT + Refresh Token |
| Caching / Token Store | Redis 7.2 |
| Logging | SLF4J + Logback (TraceId) |
| Dependency Management | Lombok |
| Testing | JUnit 5 |
| Containerization | Docker (planned) |

---

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/br/com/artheus/matchhire/
│   │   ├── controller/      → REST Controllers
│   │   ├── domain/          → Entities and Repositories
│   │   ├── dto/             → Data Transfer Objects
│   │   ├── exception/       → Custom Exceptions and Global Handler
│   │   ├── mapper/          → MapStruct Mappers
│   │   ├── security/        → JWT, Refresh Token, Filters, and Config
│   │   ├── service/         → Business Logic
│   │   └── MatchHireApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

---

## ⚙️ Configuration

Before running, configure your `application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/matchhire
spring.datasource.username=postgres
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT
api.security.token.secret=your_secret_key_here
api.security.token.expiration=3600000
api.security.refresh.expiration=604800000  # 7 days

# Redis
spring.redis.host=localhost
spring.redis.port=6379
```

---

## 🧠 Main Endpoints

### 🔑 Authentication & Users
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/users/register` | Register a new user and return `publicId` |
| `POST` | `/api/auth/login` | Authenticate user and return **access token** (body) and **refresh token** (cookie) |
| `POST` | `/api/auth/refresh` | Use refresh token from cookie to get **new access token** |
| `GET`  | `/api/users/me` | Get current user details using access token |

**Notes:**
- **Access token** → short-lived, sent in `Authorization` header
- **Refresh token** → long-lived, stored as **HttpOnly cookie** in browser/Postman

---

### 🏢 Companies
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/companies` | Create a new company |
| `GET` | `/api/companies/{publicId}` | Get a company by ID |
| `PUT` | `/api/companies/{publicId}` | Update company data |
| `DELETE` | `/api/companies/{publicId}` | Deactivate (soft delete) a company |

### 👤 Candidates
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/candidates` | Create a new candidate |
| `GET` | `/api/candidates/{publicId}` | Get candidate details |

### 💼 Jobs
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/jobs` | Create a new job posting |
| `GET` | `/api/jobs` | List all active jobs |
| `DELETE` | `/api/jobs/{publicId}` | Deactivate a job |

---

## 🧰 Run Locally

Clone the repository:

```bash
git clone https://github.com/arthsdev/matchhire-backend.git
cd matchhire-backend
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

Access the API at:  
👉 [http://localhost:8080](http://localhost:8080)

---

## 🔁 Refresh Token Flow (Postman / Browser)

1. **Register:** `POST /api/users/register`
2. **Login:** `POST /api/auth/login` → access token in body + refresh token in cookie
3. **Get current user:** `GET /api/users/me` with `Authorization: Bearer <access_token>`
4. **Simulate expired token:** call `/api/users/me` with invalid/expired access token → 401/403
5. **Refresh token:** `POST /api/auth/refresh` (cookie sent automatically) → get new `accessToken`
6. **Use new access token:** call `/api/users/me` again → returns user data

> This flow follows **best practices**: access token in header, refresh token in secure HttpOnly cookie, stored in Redis.

---

## 🧱 Next Steps

- 🐳 Add **Docker** and CI/CD pipelines
- 📘 Integrate **Swagger/OpenAPI** for API documentation
- 🧍 Expand **User and Role** management
- 🧰 Improve **global exception handling** with error codes

---

## 👨‍💻 Author

**Artheus Dev**  
[GitHub](https://github.com/arthsdev)

---

## 📜 License

This project is licensed under the **MIT License** — feel free to use and adapt it.

