# University Management System

## 📚 Project Overview

A comprehensive Java-based university management system featuring student enrollment, course management, grade calculation, and financial services. Built with **MVC architecture**, **Repository pattern**, and a **Swing GUI**.

---

## ✨ Features

### Student Management
- ✅ Create and manage student profiles
- ✅ Calculate GPA (weighted and semester-based)
- ✅ Determine academic standing (Dean's List, Probation, etc.)
- ✅ Check enrollment eligibility
- ✅ Calculate tuition fees with scholarships
- ✅ Track graduation requirements

### Course Management
- ✅ Create and manage courses
- ✅ Track enrollment and capacity
- ✅ Calculate fill rates and popularity
- ✅ Manage waitlists
- ✅ Support for online/hybrid delivery modes

### Grade Management
- ✅ Letter grade conversion (A through F)
- ✅ Weighted score calculations
- ✅ Grade curving functionality
- ✅ Honors eligibility checking
- ✅ Class rank estimation

### Financial Services
- ✅ Scholarship calculations based on GPA
- ✅ Student loan eligibility determination
- ✅ Payment plan calculations with interest
- ✅ Late fee application
- ✅ Fee waiver qualification

### Department & Faculty
- ✅ Budget allocation per student/faculty
- ✅ Faculty-to-student ratios
- ✅ Promotion and tenure eligibility
- ✅ Research productivity tracking

---

## 🏗️ Architecture

### **MVC Pattern**
- **Model**: Business entities with core logic (6 classes)
- **View**: Swing GUI components (5 classes)
- **Controller**: Request handling (4 classes)

### **Repository Pattern**
- Clean data access layer
- In-memory storage with HashMap
- CRUD operations with query methods
- 5 repository classes

### **Service Layer**
- Business logic separation
- 7 service classes
- Integration between layers

---

## 🚀 Quick Start

### Prerequisites
- **Java 11 or higher**
- **Maven 3.6+**

### Build
```bash
mvn clean compile
```

### Run
```bash
mvn exec:java -Dexec.mainClass="edu.university.UniversityManagementApp"
```

### Build JAR
```bash
mvn clean package
java -jar target/university-management-system-1.0.0.jar
```

---

## Tests Included
- Unit tests for services and repositories
- GUI component tests
- Test coverage reports
- BVT
- ECT
- CT
- Integration tests
- System tests