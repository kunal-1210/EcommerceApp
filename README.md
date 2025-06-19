# 🛍️ E-Commerce Android App

This is a basic E-Commerce Android application built using Java and Retrofit. It fetches product data from a public API and simulates the flow of browsing, adding to cart, and reviewing order summary.

---

## 📱 Features

- 🔍 Browse and search products from the [fakestoreapi.com](https://fakestoreapi.com/)
- ➕ Add products to cart (API call made)
- ❌ Remove item from cart (temporary UI removal)
- 📋 View cart with total calculation
- 🧾 Order summary with pre-filled user info after login
- 👤 Login via username and password

---

## 🔗 API Used

- Base URL: `https://fakestoreapi.com/`
- Endpoints:
  - `GET /products`
  - `POST /auth/login`
  - `GET /carts/user/{id}`
  - `GET /users`

---

## ⚠️ Important Notes

- **Add to Cart:**  
  The public API does not allow cart modification (e.g., POST/PUT), but the app simulates it by triggering a real `POST` call and displaying a toast message like _"Added to cart successfully"_.

- **Cart Removal:**  
  The cart removal only updates the local cart state and UI temporarily. It doesn't affect the backend due to API restrictions.

- **Payment Gateway:**  
  Full payment processing was excluded, as it was not required for the assignment. A placeholder message is shown on order confirmation.

---

## 🧪 Test Credentials

Use any credentials from the public API:
- **Username:** `johnd`
- **Password:** `m38rmF$`

---

## 📁 Folder Structure

