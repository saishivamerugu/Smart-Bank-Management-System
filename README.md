# Smart Bank Management System

## Overview

The **Smart Bank Management System** is a web-based banking application that enables users to manage bank accounts, perform transactions, and securely access banking services online. The system provides a seamless and secure platform for customers and administrators to handle banking operations efficiently.

--- 

## Features

### Customer Featur
- User Registration and Login
- Secure Authentication & Authorization
- Create Bank Account
- View Account Details
- Deposit Money
- Withdraw Money
- Transfer Funds
- Check Account Balance
- View Transaction History
- Update Profile Information
- Change Password

### Admin Features
- Admin Login
- View All Customers
- Manage Customer Accounts
- Activate/Deactivate Accounts
- Monitor Transactions
- Generate Reports

---

## Tech Stack  

### Backend
- Java
- Spring Boot
- Spring Security
- Hibernate / JPA
- REST APIs

### Frontend
- HTML
- CSS
- JavaScript
- Bootstrap

### Database
- MySQL

### Tools & Technologies
- Maven
- Git
- GitHub
- Postman

---

## Project Architecture

```text
Client (Browser)
       |
       v
Frontend (HTML, CSS, JavaScript)
       |
       v
Spring Boot REST APIs
       |
       v
Service Layer
       |
       v
Repository Layer (JPA/Hibernate)
       |
       v
MySQL Database
```

---

## Modules

### 1. Authentication Module
- User Registration
- User Login
- JWT Authentication
- Password Encryption

### 2. Customer Management Module
- Customer Registration
- Profile Management
- Account Management

### 3. Banking Operations Module
- Deposit Funds
- Withdraw Funds
- Fund Transfer
- Balance Inquiry

### 4. Transaction Module
- Transaction Recording
- Transaction History
- Account Statements

### 5. Admin Module
- Customer Management
- Account Monitoring
- Transaction Monitoring
- Reporting

---

## Database Design

### Users Table

| Column | Type |
|----------|----------|
| id | BIGINT |
| name | VARCHAR |
| email | VARCHAR |
| password | VARCHAR |
| role | VARCHAR |

### Accounts Table

| Column | Type |
|----------|----------|
| account_id | BIGINT |
| account_number | VARCHAR |
| account_type | VARCHAR |
| balance | DECIMAL |
| user_id | BIGINT |

### Transactions Table

| Column | Type |
|----------|----------|
| transaction_id | BIGINT |
| transaction_type | VARCHAR |
| amount | DECIMAL |
| transaction_date | TIMESTAMP |
| account_id | BIGINT |

---

## API Endpoints

### Authentication APIs

```http
POST /api/auth/register
POST /api/auth/login
```

### Account APIs

```http
POST   /api/accounts/create
GET    /api/accounts/{id}
PUT    /api/accounts/update/{id}
DELETE /api/accounts/delete/{id}
```

### Transaction APIs

```http
POST /api/transactions/deposit
POST /api/transactions/withdraw
POST /api/transactions/transfer
GET  /api/transactions/history/{accountId}
```

### Admin APIs

```http
GET /api/admin/customers
GET /api/admin/accounts
GET /api/admin/transactions
PUT /api/admin/account-status/{id}
```

---

## Installation & Setup

### Clone the Repository

```bash
git clone https://github.com/your-username/Smart-Bank-Management-System.git
```

### Navigate to Project Directory

```bash
cd Smart-Bank-Management-System
```

### Configure Database
   
Update the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## Future Enhancements

- Loan Management System
- UPI Payment Integration
- Email Notifications
- SMS Alerts
- AI-Based Fraud Detection
- Mobile Banking Application
- Banking Analytics Dashboard

---

## Project Objectives

- Automate banking operations.
- Provide secure transactions.
- Improve customer experience.
- Maintain transaction transparency.
- Ensure scalability and maintainability.

---

## Author

**Sai**

- Java Full Stack Developer
- Skilled in Java, Spring Boot, Hibernate, MySQL, JavaScript, and Web Development

---

## License

This project is developed for educational and learning purposes. Feel free to use and modify it for academic and personal projects.
