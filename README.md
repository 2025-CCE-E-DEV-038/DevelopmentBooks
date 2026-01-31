# Development Books

A clean, Test-Driven Development (TDD) implementation of the Development Books kata, exposed via a RESTful API.
The goal is to compute the lowest total price for a shopping basket of books, applying specific set discounts.

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.5.10
- **Testing:** JUnit 5, AssertJ 3.27.7, Spring Boot Test 3.5.10
- **Build Tool:** Maven

## Architecture & Design

This project follows a **Clean Architecture / Domain-Driven Design (DDD)** approach.

- **Domain Layer:** Pure Java business logic.
- **Infrastructure/API:** Spring Boot Controllers and Configuration.

## Methodology

I followed a strict **Red-Green-Refactor** TDD cycle for every feature:

1.  **Red:** Write a failing test to define the requirement. Run it and see it fail.
2.  **Green:** Write the simplest code to make the test pass. Rerun the whole test class.
3.  **Refactor:** Improve code structure and remove duplication without altering behavior. Rerun the whole test class.

*Check the git commit history to see the granular evolution of the algorithm.*

### Note to Reviewers regarding Git History
The commit messages indicate the specific phase of the TDD cycle:

- `test:` represents the **RED** phase (failing test).
- `feat:` represents the **GREEN** phase (simplest implementation to pass).
- `refactor:` represents the **REFACTOR** phase (code cleanup).
- `docs:` represents documentation updates.

## Features & Progress

###  Domain Logic (Pricing Rules)
- [ ] **Empty Basket:** Handle zero books (0 EUR).
- [ ] **Single Book:** Basic unit price (50 EUR).
- [ ] **Same Books:** No discount for multiple copies of the same book.
- [ ] **Simple Sets:**
  - [ ] 2 different books: 5% discount
  - [ ] 3 different books: 10% discount
  - [ ] 4 different books: 20% discount
  - [ ] 5 different books: 25% discount
- [ ] **Mixed Sets:** Optimization logic for complex baskets.
  - [ ] 3 different titles in a basket of 4.
  - [ ] 3 different sets of 2 copies of the same book, plus two different titles in a basket of 8.
  
### API Endpoints
- [ ] **Basket price computation endpoint:** `POST /api/basket/price`

## How to Run

No manual Maven installation is required. This project includes a **Maven Wrapper**.

### Prerequisites
* **JDK 17** (Ensure `JAVA_HOME` is configured)
* **Port 8080** must be available (default)

### 1. Run Tests
To execute the unit and integration tests:

**Mac/Linux:**
``` bash
./mvnw clean test
```

**Windows:**
``` cmd
mvnw.cmd clean test
```
### 2. Build the JAR
To generate a standalone executable JAR:

**Mac/Linux:**
``` bash
./mvnw clean package
java -jar target/developmentbooks-0.0.1-SNAPSHOT.jar
```

**Windows:**
``` cmd
mvnw.cmd clean package
java -jar target/developmentbooks-0.0.1-SNAPSHOT.jar
```
### 3. Run the Application
To compile and start the local server:

**Mac/Linux:**
``` bash
./mvnw spring-boot:run
```

**Windows:**
``` cmd
mvnw.cmd spring-boot:run
```

### 4. Usage Example
Once the server is running, you can test the basket price computation endpoint:

curl -X POST http://localhost:8080/api/basket/price \
    -H "Content-Type: application/json" \
    -d '["Clean Code", "The Clean Coder", "Clean Architecture"]'