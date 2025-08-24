# advanced-banking-management-system
Full-stack banking system: Legacy Java Swing + Modern Spring Boot REST API


# 🏦 Advanced Banking Management System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com)
[![JWT](https://img.shields.io/badge/JWT-Enabled-red.svg)](https://jwt.io)

**A comprehensive banking system showcasing evolution from legacy desktop application to modern microservices architecture.**

## 🌟 Project Highlights

### **System Modernization Journey**
- **Legacy System**: Java Swing desktop ATM application with direct JDBC
- **Modern Backend**: Spring Boot REST API with JWT authentication
- **Database Evolution**: From basic MySQL to optimized JPA schema design
- **Security Enhancement**: From basic validation to enterprise-level security

### **Key Features Implemented**
✅ **Multi-tier Architecture**: Desktop → REST API → Microservices ready  
✅ **Secure Banking**: JWT authentication + role-based access control  
✅ **Complete CRUD**: Account management, transactions, user administration  
✅ **Production Ready**: Docker containerization + CI/CD pipeline  
✅ **Enterprise Security**: BCrypt encryption, CORS protection, input validation  

## 🚀 Technical Skills Demonstrated

| **Category** | **Technologies** |
|--------------|-----------------|
| **Backend** | Spring Boot 3.2, Spring Security 6, JWT, REST APIs |
| **Database** | MySQL 8.0, Spring Data JPA, Hibernate ORM |
| **Security** | JWT Authentication, BCrypt, Role-based Access Control |
| **Architecture** | Layered Architecture, Separation of Concerns, MVC Pattern |
| **DevOps** | Docker, Docker Compose, CI/CD with GitHub Actions |
| **Legacy Integration** | Java Swing, JDBC, System Modernization |

## 📋 Quick Start


banking-management-system/
├── README.md
├── LICENSE
├── .gitignore
├── docs/
│   ├── screenshots/
│   │   ├── login-screen.png
│   │   ├── transaction-menu.png
│   │   ├── withdrawal-interface.png
│   │   └── api-documentation.png
│   ├── database/
│   │   ├── schema.sql
│   │   ├── sample-data.sql
│   │   └── database-design.md
│   ├── architecture/
│   │   ├── system-architecture.md
│   │   └── migration-strategy.md
│   └── api/
│       ├── postman-collection.json
│       └── api-documentation.md
├── legacy-swing-application/
│   ├── src/
│   │   └── bank/
│   │       └── mgmt/
│   │           ├── Signup1.java
│   │           ├── Signup2.java
│   │           ├── Signup4.java
│   │           ├── Transactions.java
│   │           ├── Withdraw.java
│   │           └── Conn.java
│   ├── resources/
│   │   └── icon/
│   │       └── atm.jpg
│   ├── lib/
│   │   └── mysql-connector-java.jar
│   └── README.md
├── spring-boot-backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── banking/
│   │   │   │           ├── BankingApplication.java
│   │   │   │           ├── config/
│   │   │   │           ├── controller/
│   │   │   │           ├── dto/
│   │   │   │           ├── entity/
│   │   │   │           ├── exception/
│   │   │   │           ├── repository/
│   │   │   │           ├── security/
│   │   │   │           └── service/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── README.md
├── frontend/
│   ├── react-banking-app/
│   │   ├── public/
│   │   ├── src/
│   │   ├── package.json
│   │   └── README.md
│   └── README.md
└── deployment/
    ├── docker/
    │   ├── Dockerfile
    │   └── docker-compose.yml
    ├── kubernetes/
    │   ├── deployment.yaml
    │   ├── service.yaml
    │   └── configmap.yaml
    └── scripts/
        ├── deploy.sh
      

