# 🧠 MatchHire Backend

**MatchHire** is a modern recruitment platform API built with **Spring Boot 3**, designed to connect candidates and companies efficiently through a scalable and secure architecture.

---

## 🚀 Overview

The project implements the core backend foundation for MatchHire, including:
- Complete CRUD operations for **Candidates**, **Companies**, and **Jobs**.
- Soft delete (`active = false`) strategy for logical data removal.
- DTOs and Mappers using **MapStruct**.
- Input validation with **Jakarta Validation**.
- Business rules and clean exception handling.
- Ready for integration with **JWT Authentication** and **Role-Based Access Control** (next steps).

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
| Logging | SLF4J + Logback |
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
│   │   ├── exception/       → Custom Exceptions
│   │   ├── mapper/          → MapStruct Mappers
│   │   ├── service/         → Business Logic
│   │   └── MatchHireApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

---

## ⚙️ Configuration

Before running, set up your `application.properties` file:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/matchhire
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# JPA
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🧠 Main Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| **POST** | `/api/companies` | Create a new company |
| **GET** | `/api/companies/{publicId}` | Get a company by public ID |
| **PUT** | `/api/companies/{publicId}` | Update a company |
| **DELETE** | `/api/companies/{publicId}` | Deactivate (soft delete) a company |
| **POST** | `/api/candidates` | Create a new candidate |
| **GET** | `/api/candidates/{publicId}` | Get candidate details |
| **POST** | `/api/jobs` | Create a new job posting |
| **GET** | `/api/jobs` | List all active jobs |
| **DELETE** | `/api/jobs/{publicId}` | Deactivate a job |

---

## 🧰 Run Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/arthsdev/matchhire-backend.git
   cd matchhire-backend
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. The API will be available at:
   ```
   http://localhost:8080
   ```

---

## 🧱 Next Steps

- [ ] Implement JWT Authentication & Role-based Authorization  
- [ ] Add User and Role entities  
- [ ] Create global exception handling with error codes  
- [ ] Add Docker and CI/CD pipelines  
- [ ] Integrate Swagger/OpenAPI for documentation  

---

## 👨‍💻 Author

**Artheus Dev**  
[GitHub](https://github.com/arthsdev)

---

## 📜 License

This project is licensed under the **MIT License** — feel free to use and adapt it.
