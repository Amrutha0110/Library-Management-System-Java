![Java](https://img.shields.io/badge/Java-8%2B-blue)
![License](https://img.shields.io/github/license/Amrutha0110/Library-Management-System-Java)
![Build Status](https://github.com/Amrutha0110/Library-Management-System-Java/actions/workflows/build.yml/badge.svg)
![Stars](https://img.shields.io/github/stars/Amrutha0110/Library-Management-System-Java?style=social)

# 📚 Library Management System (Java)

A console-based Library Management System implemented in Java using clean OOP concepts, packages, and service layers.  
Perfect for academic projects, Java practice, and learning object-oriented design.

---

## 🚀 Features
- Add, Remove, Update, and Search Books  
- Member registration and management  
- Issue & Return books (transaction handling)  
- Custom exceptions for better error handling  
- Utility classes for file handling and date formatting  
- Organized packages: `model`, `service`, `exception`, `util`

---

## 📂 Project Structure
```
src/
 └── com/
     └── library/
         ├── exception/        # Custom exception classes
         ├── model/            # Book, Member, Transaction POJOs
         ├── service/          # Service layer for core logic
         ├── util/             # Utility helpers (FileHandler, DateUtil)
         └── LibraryManagementSystem.java   # Main class (program entry)
```

---

## 🛠️ How to Compile and Run

### ✔ Using terminal (CMD/PowerShell)

### 1️⃣ Compile
```bash
javac -d out src/com/library/**/*.java src/com/library/*.java
```

If PowerShell doesn’t expand `**`, use:
```bash
javac -d out src/com/library/*.java src/com/library/exception/*.java src/com/library/model/*.java src/com/library/service/*.java src/com/library/util/*.java
```

### 2️⃣ Run
```bash
java -cp out com.library.LibraryManagementSystem
```

---

## 📘 Technologies Used
- Java 8+  
- OOP principles  
- Custom Exceptions  
- Multi-layered architecture  
- File handling (if implemented)

---

## 🔧 Future Enhancements
- GUI (JavaFX / Swing)  
- Database storage (MySQL / PostgreSQL)  
- Admin dashboard  
- Export book/transaction reports  

---

## 👩‍💻 Author
**Amrutha Varshini Devadi**  
Student | Java Developer | Learning OOP & Software Design  

⭐ *If you like this project, consider giving it a star on GitHub!*
