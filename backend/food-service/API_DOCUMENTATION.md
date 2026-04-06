# Food Service API - Mini Food Ordering System

## 📋 Giới thiệu

Đây là Food Service API cho hệ thống đặt món ăn nội bộ (Mini Food Ordering System). API cung cấp các chức năng quản lý thực đơn: xem, thêm, sửa, xóa các món ăn.

## 🚀 Công nghệ sử dụng

- **Spring Boot 3.5.13**: Framework web
- **Spring Data JPA**: ORM cho database
- **H2 Database**: In-memory database
- **Lombok**: Giảm boilerplate code
- **Maven**: Build tool

## 📝 Cấu trúc dự án

```
src/main/java/com/iuh/fit/food_service/
├── model/              # Entity classes
│   └── Food.java       # Food entity
├── repository/         # Data access layer
│   └── FoodRepository.java
├── service/            # Business logic layer
│   ├── FoodService.java
│   └── FoodServiceImpl.java
├── controller/         # REST API endpoints
│   └── FoodController.java
├── dto/
│   ├── requests/       # Request DTOs
│   │   └── CreateFoodRequest.java
│   └── responses/      # Response DTOs
│       └── FoodResponse.java
├── DataInitializer.java  # Seed data
└── FoodServiceApplication.java
```

## 🔧 Cài đặt và chạy

### 1. Build project
```bash
mvn clean install
```

### 2. Chạy ứng dụng
```bash
mvn spring-boot:run
```

Server sẽ chạy tại: `http://localhost:8080`

### 3. Truy cập H2 Console (tuỳ chọn)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:fooddb`
- Username: `sa`
- Password: (để trống)

## 📡 REST API Endpoints

### 1. Lấy danh sách tất cả món ăn
```http
GET /foods
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Cơm Gà",
    "price": 35000.0,
    "description": "Cơm gà nướng chi tiết, gà nướng mềm thơm, cơm mềm dẻo",
    "imageUrl": "https://via.placeholder.com/300x300?text=Com+Ga",
    "available": true
  },
  ...
]
```

### 2. Lấy thông tin chi tiết một món ăn
```http
GET /foods/{id}
```

**Response:**
```json
{
  "id": 1,
  "name": "Cơm Gà",
  "price": 35000.0,
  "description": "Cơm gà nướng chi tiết, gà nướng mềm thơm, cơm mềm dẻo",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Ga",
  "available": true
}
```

### 3. Thêm món ăn mới
```http
POST /foods
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Bánh Chưng",
  "price": 25000,
  "description": "Bánh chưng truyền thống, nhân thịt, hạt mầm",
  "imageUrl": "https://via.placeholder.com/300x300?text=Banh+Chung",
  "available": true
}
```

**Response (201 Created):**
```json
{
  "id": 11,
  "name": "Bánh Chưng",
  "price": 25000.0,
  "description": "Bánh chưng truyền thống, nhân thịt, hạt mầm",
  "imageUrl": "https://via.placeholder.com/300x300?text=Banh+Chung",
  "available": true
}
```

### 4. Cập nhật thông tin món ăn
```http
PUT /foods/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Cơm Gà cập nhật",
  "price": 38000,
  "description": "Cơm gà nướng mới"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Cơm Gà cập nhật",
  "price": 38000.0,
  "description": "Cơm gà nướng mới",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Ga",
  "available": true
}
```

### 5. Xóa một món ăn
```http
DELETE /foods/{id}
```

**Response (204 No Content)**

## 🧪 Kiểm tra API bằng curl hoặc Postman

### Lấy danh sách món ăn
```bash
curl http://localhost:8080/foods
```

### Lấy chi tiết một món ăn
```bash
curl http://localhost:8080/foods/1
```

### Thêm món ăn mới
```bash
curl -X POST http://localhost:8080/foods \
  -H "Content-Type: application/json" \
  -d '{"name":"Bánh Chưng","price":25000,"description":"Bánh chưng","imageUrl":"","available":true}'
```

### Cập nhật món ăn
```bash
curl -X PUT http://localhost:8080/foods/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Cơm Gà Xóa Mỡ","price":40000}'
```

### Xóa món ăn
```bash
curl -X DELETE http://localhost:8080/foods/1
```

## 📊 Dữ liệu mẫu (Seed Data)

Ứng dụng tự động tạo 10 món ăn mẫu khi khởi động:
1. Cơm Gà - 35,000 VND
2. Phở Bò - 45,000 VND
3. Bánh Mì - 20,000 VND
4. Cơm Chiên - 40,000 VND
5. Mì Xào - 35,000 VND
6. Cơm Sườn - 50,000 VND
7. Bún Chả - 40,000 VND
8. Canh Chua - 30,000 VND
9. Chè Ba Màu - 15,000 VND
10. Trà Đen - 10,000 VND

## 🏗️ Kiến trúc

- **Entity (Model)**: Định nghĩa cấu trúc dữ liệu
- **Repository**: Truy cập dữ liệu (JPA)
- **Service**: Chứa business logic
- **Controller**: REST API endpoints
- **DTO**: Data Transfer Objects cho request/response

## ❌ Error Handling

- **400 Bad Request**: Dữ liệu input không hợp lệ
- **404 Not Found**: Không tìm thấy resource
- **201 Created**: Tạo mới thành công
- **204 No Content**: Xóa thành công

## 🔜 Tiếp theo

- [ ] Có thể thêm Authentication/Authorization
- [ ] Thêm Order Service
- [ ] Thêm User Service
- [ ] Thêm Payment Gateway
- [ ] Thêm Notification Service
- [ ] Thêm caching layer

---

**Tác giả**: Mini Food Ordering System  
**Ngôn ngữ**: Java 21  
**Framework**: Spring Boot 3.5.13
