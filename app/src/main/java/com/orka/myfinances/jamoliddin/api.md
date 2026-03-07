# My Finance API Client Hujjatlari (O'zbekcha)

## 1. Umumiy ma'lumot

- Base URL: `/api/v1/`
- Autentifikatsiya: `Authorization: Bearer <access_token>`
- Content-Type: `application/json`
- Router endpointlari odatda `/` bilan tugaydi (`/companies/`, `/users/` va hokazo).
- List endpointlarida pagination sozlanmagan, natija odatda to'g'ridan-to'g'ri massiv (`[]`) ko'rinishida qaytadi.

## 2. Auth API

### 2.1 Token olish
- **POST** `/api/v1/auth/token/`

Request body:
```json
{
  "username": "admin",
  "password": "StrongPassword123"
}
```

Response body:
```json
{
  "refresh": "<refresh_token>",
  "access": "<access_token>"
}
```

### 2.2 Token yangilash
- **POST** `/api/v1/auth/token/refresh/`

Request body:
```json
{
  "refresh": "<refresh_token>"
}
```

Response body:
```json
{
  "access": "<new_access_token>",
  "refresh": "<new_refresh_token>"
}
```

## 3. Umumiy query paramlar (ko'p endpointlarda ishlaydi)

- `search=<matn>`: `search_fields` bo'yicha qidiruv
- `ordering=<field>` yoki `ordering=-<field>`: saralash
- Filtrlash uchun endpointga bog'liq `FilterSet` paramlari (har bo'limda berilgan)

---

## 4. Companies API

### 4.1 Company (`/api/v1/companies/`)

Endpointlar:
- **GET** `/api/v1/companies/` (list)
- **POST** `/api/v1/companies/` (create)
- **GET** `/api/v1/companies/{id}/` (retrieve)
- **PUT** `/api/v1/companies/{id}/` (update)
- **PATCH** `/api/v1/companies/{id}/` (partial update)
- **DELETE** `/api/v1/companies/{id}/` (delete)

Filter/search/order:
- Filter: `name`
- Search: `name`
- Ordering: `id`, `name`, `created_at`

Create/Update request body:
```json
{
  "name": "Tech Trade LLC",
  "address": "Toshkent sh., Chilonzor tumani",
  "phone": "+998901112233"
}
```

Retrieve response body:
```json
{
  "id": 1,
  "name": "Tech Trade LLC",
  "address": "Toshkent sh., Chilonzor tumani",
  "phone": "+998901112233",
  "branches": [
    {
      "id": 3,
      "name": "Yunusobod filiali",
      "address": "Yunusobod-4",
      "phone": "+998901000000",
      "company": 1,
      "created_at": "2026-03-07T10:00:00Z",
      "modified_at": "2026-03-07T10:00:00Z",
      "created_by": 2,
      "modified_by": 2
    }
  ],
  "created_at": "2026-03-07T09:00:00Z",
  "modified_at": "2026-03-07T09:10:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 4.2 Branch (`/api/v1/branches/`)

Endpointlar:
- **GET** `/api/v1/branches/`
- **POST** `/api/v1/branches/`
- **GET** `/api/v1/branches/{id}/`
- **PUT** `/api/v1/branches/{id}/`
- **PATCH** `/api/v1/branches/{id}/`
- **DELETE** `/api/v1/branches/{id}/`

Filter/search/order:
- Filter: `company`, `name`
- Search: `name`
- Ordering: `id`, `name`, `created_at`

Create/Update request body:
```json
{
  "company": 1,
  "name": "Sergeli filiali",
  "address": "Sergeli 5",
  "phone": "+998909999999"
}
```

Response body:
```json
{
  "id": 7,
  "company": 1,
  "name": "Sergeli filiali",
  "address": "Sergeli 5",
  "phone": "+998909999999",
  "created_at": "2026-03-07T11:00:00Z",
  "modified_at": "2026-03-07T11:00:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

---

## 5. Users API

### 5.1 Client (`/api/v1/clients/`)

Endpointlar:
- **GET** `/api/v1/clients/`
- **POST** `/api/v1/clients/`
- **GET** `/api/v1/clients/{id}/`
- **PUT** `/api/v1/clients/{id}/`
- **PATCH** `/api/v1/clients/{id}/`
- **DELETE** `/api/v1/clients/{id}/`

Filter/search/order:
- Filter: `first_name`, `last_name`, `phone`
- Search: `first_name`, `last_name`, `phone`
- Ordering: `id`, `first_name`, `last_name`, `created_at`

Create/Update request body:
```json
{
  "first_name": "Ali",
  "last_name": "Karimov",
  "patronymic": "Sodiqovich",
  "phone": "+998971234567",
  "address": "Toshkent"
}
```

Response body:
```json
{
  "id": 4,
  "first_name": "Ali",
  "last_name": "Karimov",
  "patronymic": "Sodiqovich",
  "phone": "+998971234567",
  "address": "Toshkent",
  "created_at": "2026-03-07T12:00:00Z",
  "modified_at": "2026-03-07T12:00:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 5.2 User (`/api/v1/users/`)

Endpointlar:
- **GET** `/api/v1/users/`
- **POST** `/api/v1/users/`
- **GET** `/api/v1/users/{id}/`
- **PUT** `/api/v1/users/{id}/`
- **PATCH** `/api/v1/users/{id}/`
- **DELETE** `/api/v1/users/{id}/`

Filter/search/order:
- Filter: `company`, `branch`, `username`, `first_name`, `last_name`, `phone`, `email`, `is_active`
- Search: `username`, `first_name`, `last_name`, `email`, `phone`
- Ordering: `id`, `username`, `first_name`, `created_at`

Create request body:
```json
{
  "username": "operator1",
  "password": "StrongPassword123",
  "first_name": "Jasur",
  "last_name": "Qodirov",
  "email": "operator1@example.com",
  "company": 1,
  "branch": 7,
  "patronymic": "Shuhrat o'g'li",
  "phone": "+998901234567",
  "address": "Toshkent",
  "profession": "Sotuvchi",
  "is_active": true
}
```

Update request body (eslatma: `company` va `branch` bu serializerda read-only):
```json
{
  "first_name": "Jasurbek",
  "last_name": "Qodirov",
  "phone": "+998901234000",
  "password": "NewStrongPass456",
  "is_active": true
}
```

Retrieve response body:
```json
{
  "id": 9,
  "username": "operator1",
  "first_name": "Jasur",
  "last_name": "Qodirov",
  "email": "operator1@example.com",
  "company": {
    "id": 1,
    "name": "Tech Trade LLC",
    "address": "Toshkent sh., Chilonzor tumani",
    "phone": "+998901112233",
    "created_at": "2026-03-07T09:00:00Z",
    "modified_at": "2026-03-07T09:10:00Z"
  },
  "branch": {
    "id": 7,
    "company": 1,
    "name": "Sergeli filiali",
    "address": "Sergeli 5",
    "phone": "+998909999999",
    "created_at": "2026-03-07T11:00:00Z",
    "modified_at": "2026-03-07T11:00:00Z",
    "created_by": 2,
    "modified_by": 2
  },
  "patronymic": "Shuhrat o'g'li",
  "phone": "+998901234567",
  "address": "Toshkent",
  "profession": "Sotuvchi",
  "is_active": true,
  "created_at": "2026-03-07T12:10:00Z",
  "modified_at": "2026-03-07T12:10:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 5.3 User Me (`/api/v1/users/me/`)

Endpointlar:
- **GET** `/api/v1/users/me/`
- **PUT** `/api/v1/users/me/`
- **PATCH** `/api/v1/users/me/`

Update request body misol:
```json
{
  "first_name": "Jasurbek",
  "phone": "+998901111111",
  "password": "AnotherPass123"
}
```

GET response body: `UserGetSerializer` formatida (`/users/{id}/` retrieve bilan bir xil).

### 5.4 User Me Company (`/api/v1/users/me/company/`)

Endpoint:
- **GET** `/api/v1/users/me/company/`

Response body: `CompanyGetSerializer` formatida (`/companies/{id}/` retrieve bilan bir xil).

Agar user kompaniyaga biriktirilmagan bo'lsa:
```json
{
  "detail": "Authenticated user does not belong to a company."
}
```

---

## 6. Products API

### 6.1 Category (`/api/v1/categories/`)

Endpointlar:
- **GET** `/api/v1/categories/`
- **POST** `/api/v1/categories/`
- **GET** `/api/v1/categories/{id}/`
- **PUT** `/api/v1/categories/{id}/`
- **PATCH** `/api/v1/categories/{id}/`
- **DELETE** `/api/v1/categories/{id}/`

Filter/search/order:
- Filter: `parent`, `is_catalog`, `name`, `branch`
- Search: `name`
- Ordering: `id`, `name`, `created_at`

Create/Update request body:
```json
{
  "branch": 7,
  "name": "Elektronika",
  "is_catalog": false,
  "parent": null,
  "template": 2
}
```

Response body:
```json
{
  "id": 11,
  "branch": 7,
  "name": "Elektronika",
  "is_catalog": false,
  "parent": null,
  "template": 2,
  "created_at": "2026-03-07T13:00:00Z",
  "modified_at": "2026-03-07T13:00:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 6.2 Template (`/api/v1/templates/`)

Endpointlar:
- **GET** `/api/v1/templates/`
- **POST** `/api/v1/templates/`
- **GET** `/api/v1/templates/{id}/`
- **PUT** `/api/v1/templates/{id}/`
- **PATCH** `/api/v1/templates/{id}/`
- **DELETE** `/api/v1/templates/{id}/`

Filter/search/order:
- Filter: `name`, `fields`
- Search: `name`
- Ordering: `id`, `name`, `created_at`

Create request body (`fields` orqali yangi fieldlar ham yaratiladi):
```json
{
  "name": "Telefon shabloni",
  "description": "Telefon mahsulotlari uchun",
  "fields": [
    {"name": "Xotira", "type": "int"},
    {"name": "Rang", "type": "string"}
  ]
}
```

Update request body (`fields` mavjud field IDlari bilan M2M update):
```json
{
  "name": "Telefon shabloni",
  "description": "Yangilangan tavsif",
  "fields": [1, 2]
}
```

Retrieve response body:
```json
{
  "id": 2,
  "name": "Telefon shabloni",
  "description": "Telefon mahsulotlari uchun",
  "fields": [
    {"id": 1, "name": "Xotira", "type": "int"},
    {"id": 2, "name": "Rang", "type": "string"}
  ],
  "created_at": "2026-03-07T13:10:00Z",
  "modified_at": "2026-03-07T13:10:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 6.3 Field (`/api/v1/fields/`)

Endpointlar:
- **GET** `/api/v1/fields/`
- **POST** `/api/v1/fields/`
- **GET** `/api/v1/fields/{id}/`
- **PUT** `/api/v1/fields/{id}/`
- **PATCH** `/api/v1/fields/{id}/`
- **DELETE** `/api/v1/fields/{id}/`

Filter/search/order:
- Filter: `name`, `type`
- Search: `name`, `type`
- Ordering: `id`, `name`, `type`, `created_at`

Create/Update request body:
```json
{
  "name": "Xotira",
  "type": "int"
}
```

Response body:
```json
{
  "id": 1,
  "name": "Xotira",
  "type": "int",
  "created_at": "2026-03-07T13:05:00Z",
  "modified_at": "2026-03-07T13:05:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 6.4 Product Title (`/api/v1/product-titles/`)

Endpointlar:
- **GET** `/api/v1/product-titles/`
- **POST** `/api/v1/product-titles/`
- **GET** `/api/v1/product-titles/{id}/`
- **PUT** `/api/v1/product-titles/{id}/`
- **PATCH** `/api/v1/product-titles/{id}/`
- **DELETE** `/api/v1/product-titles/{id}/`

Filter/search/order:
- Filter: `category`, `name`, `branch`
- Search: `name`
- Ordering: `id`, `name`, `created_at`

Create request body:
```json
{
  "category": 11,
  "name": "iPhone 14",
  "default_price": 12000000,
  "default_sale_price": 12500000,
  "description": "Asosiy model",
  "properties": [
    {"field": 1, "value": "128"},
    {"field": 2, "value": "Qora"}
  ]
}
```

Eslatma:
- `branch` read-only, backend `category.branch` dan avtomatik olinadi.
- `properties` yuborilmasa, `category.template` dagi maydonlar bo'yicha bo'sh propertylar yaratiladi.

Retrieve response body:
```json
{
  "id": 15,
  "category": 11,
  "branch": 7,
  "name": "iPhone 14",
  "default_price": 12000000,
  "default_sale_price": 12500000,
  "description": "Asosiy model",
  "properties": [
    {
      "id": 21,
      "field": {"id": 1, "name": "Xotira", "type": "int", "created_at": "2026-03-07T13:05:00Z", "modified_at": "2026-03-07T13:05:00Z"},
      "value": "128"
    }
  ],
  "created_at": "2026-03-07T13:20:00Z",
  "modified_at": "2026-03-07T13:20:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 6.5 Property (`/api/v1/properties/`)

Endpointlar:
- **GET** `/api/v1/properties/`
- **POST** `/api/v1/properties/`
- **GET** `/api/v1/properties/{id}/`
- **PUT** `/api/v1/properties/{id}/`
- **PATCH** `/api/v1/properties/{id}/`
- **DELETE** `/api/v1/properties/{id}/`

Filter/search/order:
- Filter: `product_title`, `field`, `value`
- Search: `value`, `product_title__name`, `field__name`
- Ordering: `id`, `value`, `created_at`

Create/Update request body:
```json
{
  "product_title": 15,
  "field": 1,
  "value": "256"
}
```

Response body:
```json
{
  "id": 25,
  "product_title": 15,
  "field": 1,
  "value": "256",
  "created_at": "2026-03-07T13:30:00Z",
  "modified_at": "2026-03-07T13:30:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

### 6.6 Product (`/api/v1/products/`)

Endpointlar:
- **GET** `/api/v1/products/`
- **POST** `/api/v1/products/`
- **GET** `/api/v1/products/{id}/`
- **PUT** `/api/v1/products/{id}/`
- **PATCH** `/api/v1/products/{id}/`
- **DELETE** `/api/v1/products/{id}/`

Filter/search/order:
- Filter: `product_title`, `branch`
- Search: `product_title__name`
- Ordering: `id`, `price`, `sale_price`, `created_at`

Create/Update request body:
```json
{
  "product_title": 15,
  "price": 12000000,
  "sale_price": 12500000
}
```

Eslatma:
- `branch` read-only, backend product title/category branchdan avtomatik olinadi.

Retrieve response body:
```json
{
  "id": 40,
  "product_title": {
    "id": 15,
    "category": 11,
    "branch": 7,
    "name": "iPhone 14",
    "default_price": 12000000,
    "default_sale_price": 12500000,
    "created_at": "2026-03-07T13:20:00Z",
    "modified_at": "2026-03-07T13:20:00Z"
  },
  "branch": 7,
  "price": 12000000,
  "sale_price": 12500000,
  "created_at": "2026-03-07T13:35:00Z",
  "modified_at": "2026-03-07T13:35:00Z",
  "created_by": 2,
  "modified_by": 2
}
```

---

## 7. Inventory API

### 7.1 Receive (`/api/v1/receives/`)

Endpointlar:
- **GET** `/api/v1/receives/`
- **POST** `/api/v1/receives/`
- **GET** `/api/v1/receives/{id}/`
- **PUT** `/api/v1/receives/{id}/`
- **PATCH** `/api/v1/receives/{id}/`
- **DELETE** `/api/v1/receives/{id}/`

Filter/search/order:
- Filter: `user`, `branch`, `date_time_from`, `date_time_to`
- Search: `branch__name`, `description`
- Ordering: `id`, `price`, `date_time`, `created_at`

Create request body:
```json
{
  "branch": 7,
  "price": 36000000,
  "description": "Mart oyi kirimi",
  "items": [
    {
      "product_title": 15,
      "price": 12000000,
      "sale_price": 12500000,
      "amount": 3
    }
  ]
}
```

Eslatma:
- `user` requestda yuborilmaydi, avtomatik `request.user` olinadi.
- Har bir item bo'yicha yangi `Product` yaratiladi va `StockItem.amount` oshiriladi.

Retrieve response body:
```json
{
  "id": 5,
  "user": 9,
  "branch": 7,
  "price": 36000000,
  "description": "Mart oyi kirimi",
  "items": [
    {
      "id": 12,
      "product": {
        "id": 40,
        "product_title": {
          "id": 15,
          "name": "iPhone 14",
          "default_price": 12000000,
          "default_sale_price": 12500000
        },
        "price": 12000000,
        "sale_price": 12500000
      },
      "amount": 3,
      "created_at": "2026-03-07T14:00:00Z",
      "modified_at": "2026-03-07T14:00:00Z"
    }
  ],
  "created_at": "2026-03-07T14:00:00Z",
  "modified_at": "2026-03-07T14:00:00Z",
  "created_by": 9,
  "modified_by": 9
}
```

### 7.2 Stock Item (`/api/v1/stock-items/`)

Endpointlar (faqat o'qish):
- **GET** `/api/v1/stock-items/`
- **GET** `/api/v1/stock-items/{id}/`

Filter/search/order:
- Filter: `branch`, `product`, `product_title`
- Search: `product__product_title__name`, `branch__name`
- Ordering: `id`, `amount`, `created_at`

Response body:
```json
{
  "id": 8,
  "product": {
    "id": 40,
    "product_title": 15,
    "branch": 7,
    "price": 12000000,
    "sale_price": 12500000,
    "created_at": "2026-03-07T13:35:00Z",
    "modified_at": "2026-03-07T13:35:00Z"
  },
  "branch": 7,
  "amount": 25,
  "date_time": null,
  "created_at": "2026-03-07T14:00:00Z",
  "modified_at": "2026-03-07T14:10:00Z",
  "created_by": 9,
  "modified_by": 9
}
```

---

## 8. Sales API

### 8.1 Sale (`/api/v1/sales/`)

Endpointlar:
- **GET** `/api/v1/sales/`
- **POST** `/api/v1/sales/`
- **GET** `/api/v1/sales/{id}/`
- **PUT** `/api/v1/sales/{id}/`
- **PATCH** `/api/v1/sales/{id}/`
- **DELETE** `/api/v1/sales/{id}/`

Filter/search/order:
- Filter: `user`, `client`, `branch`, `description`
- Search: `description`, `client__first_name`
- Ordering: `id`, `price`, `created_at`

Create request body:
```json
{
  "client": 4,
  "branch": 7,
  "description": "Naqd savdo",
  "items": [
    {"product": 40, "amount": 1},
    {"product": 41, "amount": 2}
  ]
}
```

Eslatma:
- `price` read-only, backendda `product.sale_price * amount` bo'yicha hisoblanadi.
- Omborda zaxira yetarli bo'lmasa `400 ValidationError` qaytadi.

Retrieve/List response body:
```json
{
  "id": 20,
  "user": 9,
  "client": 4,
  "branch": 7,
  "price": 37500000,
  "description": "Naqd savdo",
  "items": [
    {
      "id": 44,
      "product": {
        "id": 40,
        "product_title": {
          "id": 15,
          "category": 11,
          "branch": 7,
          "name": "iPhone 14",
          "default_price": 12000000,
          "default_sale_price": 12500000,
          "description": "Asosiy model",
          "created_at": "2026-03-07T13:20:00Z",
          "modified_at": "2026-03-07T13:20:00Z",
          "created_by": 2,
          "modified_by": 2
        },
        "branch": 7,
        "price": 12000000,
        "sale_price": 12500000,
        "created_at": "2026-03-07T13:35:00Z",
        "modified_at": "2026-03-07T13:35:00Z",
        "created_by": 2,
        "modified_by": 2
      },
      "amount": 1
    }
  ],
  "created_at": "2026-03-07T15:00:00Z",
  "modified_at": "2026-03-07T15:00:00Z",
  "created_by": 9,
  "modified_by": 9
}
```

### 8.2 Order (`/api/v1/orders/`)

Endpointlar:
- **GET** `/api/v1/orders/`
- **POST** `/api/v1/orders/`
- **GET** `/api/v1/orders/{id}/`
- **PUT** `/api/v1/orders/{id}/`
- **PATCH** `/api/v1/orders/{id}/`
- **DELETE** `/api/v1/orders/{id}/`

Filter/search/order:
- Filter: `user`, `client`, `branch`, `completed`, `description`, `end_date_time_from`, `end_date_time_to`
- Search: `description`, `client__first_name`
- Ordering: `id`, `price`, `created_at`, `end_date_time`, `completed`

Create request body:
```json
{
  "client": 4,
  "branch": 7,
  "price": 25000000,
  "end_date_time": "2026-03-20T18:00:00Z",
  "description": "Bo'lib to'lashga buyurtma",
  "items": [
    {"product": 40, "amount": 2}
  ]
}
```

Eslatma:
- `completed` read-only, create vaqtida doim `false` bo'ladi.

Retrieve/List response body:
```json
{
  "id": 31,
  "user": {
    "id": 9,
    "username": "operator1",
    "first_name": "Jasur",
    "last_name": "Qodirov"
  },
  "client": {
    "id": 4,
    "first_name": "Ali",
    "last_name": "Karimov",
    "phone": "+998971234567"
  },
  "branch": {
    "id": 7,
    "company": 1,
    "name": "Sergeli filiali",
    "address": "Sergeli 5",
    "phone": "+998909999999",
    "created_at": "2026-03-07T11:00:00Z",
    "modified_at": "2026-03-07T11:00:00Z",
    "created_by": 2,
    "modified_by": 2
  },
  "price": 25000000,
  "end_date_time": "2026-03-20T18:00:00Z",
  "completed": false,
  "description": "Bo'lib to'lashga buyurtma",
  "items": [
    {
      "id": 61,
      "order": 31,
      "product": {
        "id": 40,
        "product_title": 15,
        "branch": 7,
        "price": 12000000,
        "sale_price": 12500000,
        "created_at": "2026-03-07T13:35:00Z",
        "modified_at": "2026-03-07T13:35:00Z"
      },
      "amount": 2,
      "created_at": "2026-03-07T15:30:00Z",
      "modified_at": "2026-03-07T15:30:00Z",
      "created_by": 9,
      "modified_by": 9
    }
  ],
  "created_at": "2026-03-07T15:30:00Z",
  "modified_at": "2026-03-07T15:30:00Z",
  "created_by": 9,
  "modified_by": 9
}
```

---

## 9. Xatolik formatlari (odatdagi)

### 9.1 Validation xatolik
```json
{
  "field_name": [
    "Ushbu maydon majburiy."
  ]
}
```

### 9.2 Ruxsat/Token xatolik
```json
{
  "detail": "Authentication credentials were not provided."
}
```

### 9.3 Topilmadi
```json
{
  "detail": "Not found."
}
```

## 10. Qisqa texnik eslatmalar

- `created_by` va `modified_by` maydonlari backend tomonidan avtomatik qo'yiladi (read-only).
- `created_at` va `modified_at` avtomatik timestamp.
- `PUT` to'liq yangilash, `PATCH` qisman yangilash uchun.
- `DELETE` odatda `204 No Content` qaytaradi (body bo'lmaydi).
