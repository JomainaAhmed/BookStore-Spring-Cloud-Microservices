# 📘 BookStore Microservices (Spring Cloud + PostgreSQL)

A beginner-friendly microservices project built using Spring Boot and Spring Cloud.  
This system simulates an online bookstore with Book Service and Order Service, connected via Eureka, API Gateway, and Feign Client.

---

## 🚀 Tech Stack

- Java 17  
- Spring Boot 3  
- Spring Cloud (Eureka, Gateway, OpenFeign)  
- PostgreSQL 
- Maven  

---

## 🧠 Architecture Overview

### 🔹 Services

| Service         | Port | Description                    |
|----------------|------|--------------------------------|
| Eureka Server  | 8761 | Service registry               |
| API Gateway    | 8080 | Entry point for all requests   |
| Book Service   | 8081 | Manages book catalog           |
| Order Service  | 8082 | Handles orders                 |

---

## 🔄 How It Works

1. Client sends request → API Gateway (8080)  
2. Gateway routes using Eureka  
3. Order Service uses Feign Client to call Book Service  
4. Book data is fetched → Order is created  

---

## 📦 Microservices Design

- Each service has its own database  
- Each service handles one responsibility  
- No shared DB ❌  
- Communication via REST + Feign ✅  

---

## 🗄️ PostgreSQL Setup

```sql
CREATE DATABASE bookdb;
CREATE DATABASE orderdb;
```

## Application Properties

# Book Service 

server.port=8081
spring.application.name=book-service

spring.datasource.url=jdbc:postgresql://localhost:5432/bookdb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

# Order Service 

server.port=8082
spring.application.name=order-service

spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

# API Gateway

server.port=8080
spring.application.name=api-gateway

spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true

spring.cloud.gateway.routes[0].id=book-service-route
spring.cloud.gateway.routes[0].uri=lb://book-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/books/**
spring.cloud.gateway.routes[0].filters[0]=StripPrefix=0

spring.cloud.gateway.routes[1].id=order-service-route
spring.cloud.gateway.routes[1].uri=lb://order-service
spring.cloud.gateway.routes[1].predicates[0]=Path=/api/orders/**
spring.cloud.gateway.routes[1].filters[0]=StripPrefix=0

eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Eureka Server

server.port=8761
spring.application.name=eureka-server

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

# Project Structure

```
bookstore-microservices/
│
├── eureka-server/
├── api-gateway/
├── book-service/
└── order-service/

```

# API Endpoints

## Book Service

GET /api/books

GET /api/books/{id}

POST /api/books

PUT /api/books/{id}

DELETE /api/books/{id}


## Order Service

GET /api/orders

GET /api/orders/{id}

POST /api/orders

PUT /api/orders/{id}

DELETE /api/orders/{id}

# Key Learnings

1. Microservices architecture
2. Service discovery (Eureka)
3. API Gateway routing
4. Feign Client communication
5. Database per service design
