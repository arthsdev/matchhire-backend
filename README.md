# 🧠 MatchHire Backend

**MatchHire** is a modern recruitment platform API built with **Spring Boot 3**, designed to connect candidates and companies efficiently through a **secure, scalable, and maintainable architecture**.

---

## 🚀 Overview

The backend provides the foundation for the MatchHire platform, including:

- 🔐 **JWT Authentication** with Spring Security 6
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
| Security | Spring Security + JWT |
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
│   │   ├── security/        → JWT, Filters, and Config
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
```

---

## 🧠 Main Endpoints

### 🔑 Authentication
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/auth/login` | Authenticate user and return JWT |
| `POST` | `/api/auth/register` | Register a new user |

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

## 🧱 Next Steps

- 🔁 Implement **Refresh Token** flow
- 🧍 Add **User and Role** entities
- 🧰 Add **global exception handling** with error codes
- 🐳 Add **Docker** and CI/CD pipelines
- 📘 Integrate **Swagger/OpenAPI** for API documentation

---

## 👨‍💻 Author

**Artheus Dev**  
[GitHub](https://github.com/arthsdev)

---

## 📜 License

This project is licensed under the **MIT License** — feel free to use and adapt it.
