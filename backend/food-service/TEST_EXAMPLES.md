## API Test Examples

### 1. Lấy tất cả món ăn
```
GET /foods
```

### 2. Lấy thông tin chi tiết một món ăn
```
GET /foods/1
```

### 3. Thêm một món ăn mới - Ví dụ 1
```
POST /foods
Content-Type: application/json

{
  "name": "Cơm Tấm",
  "price": 32000,
  "description": "Cơm tấm nước mắm cha cua",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Tam",
  "available": true
}
```

### 4. Thêm một món ăn mới - Ví dụ 2
```
POST /foods
Content-Type: application/json

{
  "name": "Lẩu Thái",
  "price": 85000,
  "description": "Lẩu Thái cay nồn, tươi ngon",
  "imageUrl": "https://via.placeholder.com/300x300?text=Lau+Thai",
  "available": true
}
```

### 5. Thêm một món ăn mới - Ví dụ 3
```
POST /foods
Content-Type: application/json

{
  "name": "Xôi Gà",
  "price": 28000,
  "description": "Xôi gà vàng ươm, gà luộc mềm",
  "imageUrl": "https://via.placeholder.com/300x300?text=Xoi+Ga",
  "available": true
}
```

### 6. Cập nhật giá món ăn
```
PUT /foods/1
Content-Type: application/json

{
  "price": 40000
}
```

### 7. Cập nhật toàn bộ thông tin món ăn
```
PUT /foods/1
Content-Type: application/json

{
  "name": "Cơm Gà Nướng Siêu Ngon",
  "price": 42000,
  "description": "Cơm gà nướng 100% ngon thơm, gà mềm thơm",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Ga+Siêu+Ngon",
  "available": true
}
```

### 8. Xóa một món ăn
```
DELETE /foods/3
```

### 9. Đánh dấu một món ăn không còn có sẵn
```
PUT /foods/5
Content-Type: application/json

{
  "available": false
}
```

### 10. Kích hoạt lại một món ăn
```
PUT /foods/5
Content-Type: application/json

{
  "available": true
}
```

## Response Examples

### Success - GET All Foods (200 OK)
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
  {
    "id": 2,
    "name": "Phở Bò",
    "price": 45000.0,
    "description": "Phở bò truyền thống, nước dùng đậm đà, noodle mềm",
    "imageUrl": "https://via.placeholder.com/300x300?text=Pho+Bo",
    "available": true
  }
]
```

### Success - POST (201 Created)
```json
{
  "id": 11,
  "name": "Cơm Tấm",
  "price": 32000.0,
  "description": "Cơm tấm nước mắm cha cua",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Tam",
  "available": true
}
```

### Success - PUT (200 OK)
```json
{
  "id": 1,
  "name": "Cơm Gà Nướng Siêu Ngon",
  "price": 42000.0,
  "description": "Cơm gà nướng 100% ngon thơm, gà mềm thơm",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Ga+Siêu+Ngon",
  "available": true
}
```

### Success - DELETE (204 No Content)
```
(No body, just status 204)
```

### Error - NOT FOUND (404 Not Found)
```
GET /foods/999
```
Response: 404 Not Found

---

## Sử dụng Postman

### 1. Tạo Collection mới
- Tên: "Food Service API"

### 2. Tạo requests

#### Request 1: Get All Foods
- Method: GET
- URL: `{{BASE_URL}}/foods`
- Headers: Không cần

#### Request 2: Get Food by ID
- Method: GET
- URL: `{{BASE_URL}}/foods/{{FOOD_ID}}`
- Headers: Không cần

#### Request 3: Create Food
- Method: POST
- URL: `{{BASE_URL}}/foods`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "name": "Cơm Tấm",
  "price": 32000,
  "description": "Cơm tấm nước mắm cha cua",
  "imageUrl": "https://via.placeholder.com/300x300?text=Com+Tam",
  "available": true
}
```

#### Request 4: Update Food
- Method: PUT
- URL: `{{BASE_URL}}/foods/{{FOOD_ID}}`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "name": "Tên mới",
  "price": 30000
}
```

#### Request 5: Delete Food
- Method: DELETE
- URL: `{{BASE_URL}}/foods/{{FOOD_ID}}`
- Headers: Không cần

### 3. Thiết lập Environment Variables
- Tạo Environment "Local"
- Variable:
  - `BASE_URL`: `http://localhost:8080`
  - `FOOD_ID`: `1`

---

## Từ Command Line (curl)

### Windows (PowerShell)
```powershell
# GET
Invoke-RestMethod -Uri http://localhost:8080/foods -Method GET

# POST
$body = @{
    name = "Cơm Tấm"
    price = 32000
    description = "Ngon ngon"
    available = $true
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/foods -Method POST `
  -Headers @{"Content-Type"="application/json"} -Body $body

# PUT
Invoke-RestMethod -Uri http://localhost:8080/foods/1 -Method PUT `
  -Headers @{"Content-Type"="application/json"} `
  -Body (@{price = 40000} | ConvertTo-Json)

# DELETE
Invoke-RestMethod -Uri http://localhost:8080/foods/1 -Method DELETE
```

### Linux/Mac (shell)
```bash
# GET
curl http://localhost:8080/foods

# POST
curl -X POST http://localhost:8080/foods \
  -H "Content-Type: application/json" \
  -d '{"name":"Cơm Tấm","price":32000,"description":"Ngon","available":true}'

# PUT
curl -X PUT http://localhost:8080/foods/1 \
  -H "Content-Type: application/json" \
  -d '{"price":40000}'

# DELETE
curl -X DELETE http://localhost:8080/foods/1
```

---

## Lưu ý
- **Base URL**: http://localhost:8080
- **Content-Type**: application/json (cho POST/PUT)
- **Port mặc định**: 8080 (có thể đổi trong application.properties)
- **Database**: H2 In-Memory (dữ liệu mất khi restart)
