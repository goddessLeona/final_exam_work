
# Backend Code Standards

This document defines coding standards and architectural guidelines for the backend of the project. The goal is to ensure **consistency, readability, maintainability, and scalability** across the codebase.

---

## 1. Architecture & Layered Flow

The backend follows a **layered architecture** with clear separation of responsibilities.

### Standard Request Flow

```
Controller
 → Service
   → DTO Mapping
     → Repository
```

### Layer Responsibilities

#### Controller Layer

* Handles HTTP requests and responses
* Performs **request validation** (`@Valid`)
* Uses **request/response DTOs only** (no entities)
* Delegates business logic to services
* Returns appropriate HTTP status codes

**Must not:**

* Contain business logic
* Access repositories directly

---

#### Service Layer

* Contains all **business logic**
* Coordinates repositories and domain rules
* Handles transactional boundaries (`@Transactional`)
* Performs authorization checks when needed

**Logging:**

* Log errors and exceptions
* Log important business events (e.g. content published, contributor approved)

---

#### DTOs

##### Request DTOs

* Used for incoming data from clients
* Validated using Jakarta Validation annotations
* No business logic

##### Response DTOs

* Used for outgoing API responses
* Should not expose internal IDs or sensitive fields

---

#### DTO Mapping

* Maps between entities and DTOs
* Mapping logic must not exist in controllers
* Can be implemented using:

    * Dedicated mapper classes
    * MapStruct (if adopted later)

---

#### Repository Layer

* Uses Spring Data JPA
* Contains **only database access logic**
* No business logic
* Prefer method naming conventions over custom queries

---

## 2. Error Handling Strategy

### Exception Handling

* Use **custom exceptions** for business errors (e.g. `ContributorNotApprovedException`)
* Avoid throwing generic `RuntimeException`

### Global Exception Handler

* Centralized using `@ControllerAdvice`
* Maps exceptions to meaningful HTTP responses

### Logging Rules

* Errors are logged in the **service layer**
* Do not log stack traces in controllers
* Avoid logging sensitive data (tokens, passwords, personal info)

---

## 3. Logging Standards

### Log Levels

* `INFO`: Important application events
* `DEBUG`: Development-only details
* `WARN`: Recoverable issues
* `ERROR`: Exceptions and failures

### Logging Guidelines

* Use SLF4J (`@Slf4j`)
* Log:

    * Contributor approval actions
    * Content upload/removal
    * Authentication-related events (without sensitive data)

---

## 4. Naming Conventions

### Classes

* Controller: `XController`
* Service: `XService`
* Service implementation: `XServiceImpl` (if needed)
* Repository: `XRepository`
* DTOs: `XRequestDto`, `XResponseDto`

### Methods

* Use verbs for methods (`createContent`, `approveContributor`)
* Avoid abbreviations

---

## 5. Package Structure

```
com.projectname
 ├── config
 ├── controller
 ├── service
 ├── dto
 │    ├── request
 │    ├── response
 │    └── mapper
 ├── entity
 ├── repository
 ├── exception
 └── security
```

---

## 6. Validation Rules

* Use annotation-based validation (`@NotNull`, `@NotBlank`, `@Size`, etc.)
* Validation occurs in request DTOs
* Controller must reject invalid requests before reaching services

---

## 7. Security Standards

* JWT-based authentication
* Role-based authorization using Spring Security
* Controllers protected via annotations (`@PreAuthorize`)
* No security logic inside repositories

---

## 8. Transaction Management

* Use `@Transactional` in service layer only
* Read-only transactions for read operations where applicable

---

## 9. Code Quality Guidelines

* Follow SOLID principles
* Keep methods small and focused
* Avoid duplication
* Prefer immutability where possible
* Write readable code over clever code

---

## 10. Testing Guidelines (Optional but Recommended)

* Unit test services
* Mock repositories
* Avoid testing controllers with business logic

---

## 11. Documentation

* Public methods should be self-explanatory
* Complex logic must be commented
* API endpoints documented via Swagger/OpenAPI (if enabled), if time :)

---

## 12. Out of Scope for MVP

* Microservices architecture
* Event-driven messaging
* Advanced caching mechanisms
