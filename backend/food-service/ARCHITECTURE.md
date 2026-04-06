# 🏗️ Architecture & Project Structure

## Overall Architecture Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    Client Applications                  │
│                 (Browser, Postman, Mobile)              │
└────────────────────────┬────────────────────────────────┘
                         │
                 HTTP/REST Methods
                         │
         ┌───────────────┼───────────────┐
         │               │               │
      GET /foods    POST /foods    PUT/DELETE
    getAllFoods    createFood     updateFood
         │               │         deleteFood
         └───────────────┼───────────────┘
                         │
         ╔═══════════════════════════════════╗
         ║      FoodController (REST API)    ║
         ║  @RestController                  ║
         ║  @RequestMapping("/foods")        ║
         ╚═══════════════════════════════════╝
                         │
         ┌───────────────────────────────────┐
         │    Handles HTTP Requests/Responses│
         │    Returns JSON                   │
         └───────────────┬───────────────────┘
                         │
         ╔═══════════════════════════════════╗
         ║     FoodService (Business Logic)  ║
         ║  @Service (FoodServiceImpl)        ║
         ╚═══════════════════════════════════╝
                         │
         ┌───────────────────────────────────┐
         │    - getAllFoods()                │
         │    - getFoodById(Long id)         │
         │    - createFood(Food)             │
         │    - updateFood(Long, Food)       │
         │    - deleteFood(Long)             │
         └───────────────┬───────────────────┘
                         │
         ╔═══════════════════════════════════╗
         ║   FoodRepository (Data Access)    ║
         ║   extends JpaRepository<Food,Long>║
         ╚═══════════════════════════════════╝
                         │
         ┌───────────────────────────────────┐
         │    CRUD Operations via JPA        │
         │    - save()                       │
         │    - findById()                   │
         │    - findAll()                    │
         │    - deleteById()                 │
         └───────────────┬───────────────────┘
                         │
         ╔═══════════════════════════════════╗
         ║      H2 Database (In-Memory)      ║
         ║    Table: FOODS                   ║
         ║    - id (PK)                      │
         ║    - name                         │
         ║    - price                        │
         ║    - description                  │
         ║    - imageUrl                     │
         ║    - available                    │
         ╚═══════════════════════════════════╝
```

---

## Layer Architecture

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  (FoodController)                       │
│  ↓ Receives HTTP requests               │
│  ↓ Returns JSON responses               │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────┴──────────────────────┐
│         Business Logic Layer             │
│  (FoodServiceImpl)                        │
│  ↓ Validates data                        │
│  ↓ Processes business rules              │
│  ↓ Handles exceptions                    │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────┴──────────────────────┐
│         Data Access Layer                │
│  (FoodRepository)                        │
│  ↓ Queries database                      │
│  ↓ Persists data                         │
│  ↓ ORM operations                        │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────┴──────────────────────┐
│         Database Layer                   │
│  (H2 In-Memory Database)                 │
│  ↓ Data storage                          │
│  ↓ SQL execution                         │
└──────────────────────────────────────────┘
```

---

## Request Flow Example

### Scenario: Create a new Food

```
1. CLIENT REQUEST
   POST http://localhost:8080/foods
   Content-Type: application/json
   Body: {
     "name": "Cơm Tấm",
     "price": 32000,
     "description": "Cơm tấm nước mắm",
     "available": true
   }

2. DISPATCHER SERVLET
   ↓ Routes to FoodController.createFood()

3. CONTROLLER LAYER
   @PostMapping
   public ResponseEntity<Food> createFood(@RequestBody Food food)
   ↓ Passes to service

4. SERVICE LAYER
   @Service
   public Food createFood(Food food)
   ↓ Business logic validation
   ↓ Passes to repository

5. REPOSITORY LAYER
   foodRepository.save(food)
   ↓ JPA translates to SQL

6. DATABASE LAYER
   INSERT INTO FOODS (name, price, description, available)
   VALUES ('Cơm Tấm', 32000, 'Cơm tấm nước mắm', true)
   ↓ Returns generated ID

7. RESPONSE CHAIN
   ← Food with ID from database
   ← ResponseEntity with HTTP 201 CREATED
   ← JSON response to client

8. CLIENT RESPONSE
   HTTP/1.1 201 Created
   Content-Type: application/json
   Body: {
     "id": 11,
     "name": "Cơm Tấm",
     "price": 32000.0,
     "description": "Cơm tấm nước mắm",
     "imageUrl": null,
     "available": true
   }
```

---

## File Structure & Dependencies

```
food-service/
│
├── pom.xml
│   ├── spring-boot-starter-web
│   ├── spring-boot-starter-data-jpa
│   ├── h2
│   └── lombok
│
├── src/main/java/com/iuh/fit/food_service/
│   │
│   ├── FoodServiceApplication.java
│   │   @SpringBootApplication
│   │   Main entry point
│   │
│   ├── DataInitializer.java
│   │   @Configuration
│   │   @Bean CommandLineRunner
│   │   Seed 10 foods on startup
│   │
│   ├── model/
│   │   └── Food.java
│   │       @Entity
│   │       Fields: id, name, price, description, imageUrl, available
│   │
│   ├── repository/
│   │   └── FoodRepository.java
│   │       extends JpaRepository<Food, Long>
│   │
│   ├── service/
│   │   ├── FoodService.java (interface)
│   │   │   - getAllFoods()
│   │   │   - getFoodById(Long)
│   │   │   - createFood(Food)
│   │   │   - updateFood(Long, Food)
│   │   │   - deleteFood(Long)
│   │   │
│   │   └── FoodServiceImpl.java (@Service)
│   │       Implements FoodService
│   │
│   ├── controller/
│   │   └── FoodController.java
│   │       @RestController
│   │       @RequestMapping("/foods")
│   │       GET /foods
│   │       POST /foods
│   │       PUT /foods/{id}
│   │       DELETE /foods/{id}
│   │
│   └── dto/
│       ├── requests/
│       │   └── CreateFoodRequest.java
│       └── responses/
│           └── FoodResponse.java
│
└── src/main/resources/
    └── application.properties
        - spring.application.name=food-service
        - server.port=8080
        - spring.datasource.url=jdbc:h2:mem:fooddb
        - spring.jpa.hibernate.ddl-auto=create-drop
        - spring.h2.console.enabled=true
```

---

## Dependency Injection Diagram

```
Application Context
│
├── FoodRepository
│   (Spring Data JPA auto-implements)
│
├── FoodService
│   @Service: FoodServiceImpl
│   @Autowired: FoodRepository
│
├── FoodController
│   @RestController
│   @Autowired: FoodService
│
└── DataInitializer
    @Configuration
    @Bean: CommandLineRunner(FoodRepository)
```

---

## Data Flow (CRUD Operations)

### C - CREATE
```
Controller.createFood(Food)
  → Service.createFood(Food)
    → Repository.save(Food)
      → Database INSERT
        → Return Food with ID
```

### R - READ
```
Controller.getAllFoods() OR getFoodById(Long)
  → Service.getAllFoods() OR getFoodById(Long)
    → Repository.findAll() OR findById(Long)
      → Database SELECT
        → Return Food(s)
```

### U - UPDATE
```
Controller.updateFood(Long, Food)
  → Service.updateFood(Long, Food)
    → Repository.findById(Long)
      → (Check existence)
      → Update fields
      → Repository.save(Food)
        → Database UPDATE
          → Return updated Food
```

### D - DELETE
```
Controller.deleteFood(Long)
  → Service.deleteFood(Long)
    → Repository.existsById(Long)
      → (Check existence)
      → Repository.deleteById(Long)
        → Database DELETE
          → Return 204 No Content
```

---

## REST API Endpoint Summary

| Method | URL          | Service Method   | Response | Status |
|--------|--------------|------------------|----------|--------|
| GET    | /foods       | getAllFoods()    | List[]   | 200    |
| GET    | /foods/{id}  | getFoodById()    | Food     | 200    |
| POST   | /foods       | createFood()     | Food     | 201    |
| PUT    | /foods/{id}  | updateFood()     | Food     | 200    |
| DELETE | /foods/{id}  | deleteFood()     | -        | 204    |

---

## Technology Stack

```
┌─ JAVA 21
├─ SPRING BOOT 3.5.13
│  ├─ spring-web (MVC)
│  ├─ spring-data-jpa (ORM)
│  └─ spring-core (Dependency Injection)
├─ HIBERNATE (JPA Implementation)
├─ H2 Database (In-Memory)
├─ LOMBOK (Annotation Processing)
├─ MAVEN (Build Tool)
└─ Jackson (JSON Processing)
```

---

## Environment Configuration

### application.properties
```properties
# Application Name
spring.application.name=food-service

# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:fooddb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Example: Complete Request Lifecycle

### REQUEST
```
POST /foods
Content-Type: application/json

{
  "name": "Phở Chay",
  "price": 30000,
  "description": "Phở chay ngon lành",
  "available": true
}
```

### 1. Spring DispatcherServlet receives
### 2. Routes to FoodController.createFood()
### 3. @RequestBody deserializes JSON → Food object
### 4. Controller calls foodService.createFood(food)
### 5. Service calls foodRepository.save(food)
### 6. Hibernate translates to:
```sql
INSERT INTO FOODS 
(AVAILABLE, DESCRIPTION, NAME, PRICE, IMAGE_URL) 
VALUES (true, 'Phở chay ngon lành', 'Phở Chay', 30000.0, NULL)
```

### 7. H2 Database executes INSERT
### 8. Returns Food with generated ID
### 9. Service returns Food with ID
### 10. Controller wraps in ResponseEntity(201)
### 11. Jackson serializes to JSON
### 12. Spring returns HTTP response

### RESPONSE
```
HTTP/1.1 201 Created
Content-Type: application/json;charset=UTF-8

{
  "id": 11,
  "name": "Phở Chay",
  "price": 30000.0,
  "description": "Phở chay ngon lành",
  "imageUrl": null,
  "available": true
}
```

---

## Best Practices Implemented

✅ **Separation of Concerns**: Controller → Service → Repository  
✅ **Dependency Injection**: @Autowired  
✅ **Layered Architecture**: Presentation → Business → Data  
✅ **REST Conventions**: GET, POST, PUT, DELETE  
✅ **HTTP Status Codes**: 200, 201, 204, 404  
✅ **JSON Serialization**: Automatic with Jackson  
✅ **CORS Support**: @CrossOrigin for client-side requests  
✅ **ORM Pattern**: JPA/Hibernate  
✅ **Repository Pattern**: Data access abstraction  
✅ **Lombok**: Reduced boilerplate (@Data, @Builder, etc.)  

---

This architecture is scalable, maintainable, and follows Spring Boot best practices!
