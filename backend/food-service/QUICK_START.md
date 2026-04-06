# 🚀 Quick Start Guide - Food Service API

## Bước 1: Chuẩn bị môi trường

### Yêu cầu
- Java 21+
- Maven 3.6+

### Kiểm tra việc cài đặt
```bash
java -version
mvn -version
```

## Bước 2: Build & Run

### Cách 1: Sử dụng Maven
```bash
cd d:\IUH\KTVTKPM\food-service
mvn clean install
mvn spring-boot:run
```

### Cách 2: Chạy từ IDE
- Mở project trong IntelliJ IDEA hoặc Eclipse
- Click chuột phải vào `FoodServiceApplication.java`
- Chọn "Run"

## Bước 3: Kiểm tra server

Truy cập: `http://localhost:8080/foods`

Nếu thấy danh sách JSON → Server đang chạy ✅

## 📱 Kiểm tra API

### Option 1: Sử dụng Browser
```
http://localhost:8080/foods
```

### Option 2: Sử dụng Postman/Insomnia
1. Tải Postman/Insomnia
2. Import các request từ mục dưới

### Option 3: Sử dụng curl (Windows/Mac/Linux)

#### GET - Lấy tất cả món ăn
```bash
curl -X GET http://localhost:8080/foods
```

#### GET - Lấy chi tiết
```bash
curl -X GET http://localhost:8080/foods/1
```

#### POST - Thêm món ăn mới
```bash
curl -X POST http://localhost:8080/foods ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Cơm Tấm\",\"price\":30000,\"description\":\"Cơm tấm nước mắm\",\"available\":true}"
```

#### PUT - Cập nhật
```bash
curl -X PUT http://localhost:8080/foods/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"price\":40000,\"description\":\"Cập nhật giá mới\"}"
```

#### DELETE - Xóa
```bash
curl -X DELETE http://localhost:8080/foods/1
```

## 📊 H2 Database Console

1. Truy cập: `http://localhost:8080/h2-console`
2. Thiết lập kết nối:
   - **JDBC URL**: `jdbc:h2:mem:fooddb`
   - **Username**: `sa`
   - **Password**: (để trống)
3. Click "Connect"
4. Chạy query SQL:
   ```sql
   SELECT * FROM FOODS;
   ```

## 🗂️ Cấu trúc folder chính

```
src/main/java/com/iuh/fit/food_service/
├── model/
│   └── Food.java          ← Entity (lõi dữ liệu)
├── repository/
│   └── FoodRepository.java ← Database queries
├── service/
│   ├── FoodService.java    ← Interface service
│   └── FoodServiceImpl.java ← Implement logic
├── controller/
│   └── FoodController.java ← REST API
└── DataInitializer.java    ← Seed data (tự động tạo)
```

## ✅ Danh sách Food có sẵn

| ID | Tên         | Giá      |
|----|-------------|----------|
| 1  | Cơm Gà      | 35,000   |
| 2  | Phở Bò      | 45,000   |
| 3  | Bánh Mì     | 20,000   |
| 4  | Cơm Chiên   | 40,000   |
| 5  | Mì Xào      | 35,000   |
| 6  | Cơm Sườn    | 50,000   |
| 7  | Bún Chả     | 40,000   |
| 8  | Canh Chua   | 30,000   |
| 9  | Chè Ba Màu  | 15,000   |
| 10 | Trà Đen     | 10,000   |

## 🐛 Troubleshooting

### Server không khởi động
```bash
# Kiểm tra port 8080 đã bị chiếm?
netstat -ano | findstr :8080

# Nếu port bị chiếm, thay đổi trong application.properties
# server.port=8081
```

### Lỗi database
```bash
# H2 là in-memory, dữ liệu mất sau khi restart
# Chạy lại để tạo seed data mới
mvn clean spring-boot:run
```

### Import Postman Collection

Tạo file `postman_collection.json`:

```json
{
  "info": {"name": "Food Service API"},
  "item": [
    {
      "name": "Get All Foods",
      "request": {"method": "GET", "url": "http://localhost:8080/foods"}
    },
    {
      "name": "Get Food by ID",
      "request": {"method": "GET", "url": "http://localhost:8080/foods/1"}
    },
    {
      "name": "Create Food",
      "request": {
        "method": "POST",
        "url": "http://localhost:8080/foods",
        "body": {"name":"Test","price":25000,"description":"Test"}
      }
    }
  ]
}
```

## 🎯 Tiếp theo

- [ ] Thêm authentication
- [ ] Kết nối với Order Service
- [ ] Add caching
- [ ] Docker deployment

---

**Cần giúp?** Kiểm tra `API_DOCUMENTATION.md` để xem chi tiết lệnh.
