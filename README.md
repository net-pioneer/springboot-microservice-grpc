# 🚀 Spring Boot Microservice Personal Structure  
**by Pouya**

This repository showcases a personal microservice architecture built using Spring Boot, focusing on clean structure, scalability, and reusable components.
(hardly managed whole authenticate system use spring securiry)
---

## 🔐 Authentication System

The authentication system is lightweight and inspired by Laravel Sanctum !.  
It provides token-based authentication for users.

The entire authentication flow is implemented using Spring Security, including:
- User authentication  
- Token validation  
- Request authorization  

---

## ⚙️ Installation Guide

1. Open your IDE and locate the **Gradle** panel  
2. Run: GenerateProto
3. cd MainService And run gradlew.bat bootRun --args="--seed_user" and gradlew.bat bootRun --args="--seed_cn"
5.  Start the main service

---

## 🔌 RPC Client Requests

### ✅ Authorization in RPC

Use the following annotation to require authenticated access in RPC calls:
(ex:NotebookClient in OrdersService)
```java
@AuthRequest
```

🔒 Role-Based Access Control

To restrict access based on user roles, use:
to See example visit SupplyController in OrdersService
```java
@HasRole(role = UserRoles.SUPPLIER)
```
