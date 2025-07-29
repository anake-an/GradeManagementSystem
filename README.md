# 🎓 Grade Management System
![License](https://img.shields.io/badge/license-MIT-blue.svg)

A Java Swing-based desktop application designed to manage student academic records with login authentication. This project was developed using **Object-Oriented Programming (OOP)** concepts and a graphical interface designed via **IntelliJ Swing UI Designer**.

Users can log in through credentials stored in a simple `Login.txt` file, register new students, view and update their grades, and export transcripts — all through a friendly GUI.

---

## 💡 Project Overview

This application was originally created as part of an academic group project for the **Object-Oriented Programming (KP14203)** course at Universiti Malaysia Pahang (UMP).

It has since been cleaned, restructured, and published here as a portfolio project by the contributor — with proper credit to the original group members.

---

## 🚀 Features

- 🔐 **Login authentication** using `Login.txt` (no hardcoded credentials)
- 🧑 **Register** new student profiles with multiple subject scores
- ✏️ **Update** existing student data
- 📋 **View** student list with sorting and access options
- 🖨️ **Export** transcript text files by student ID
- ❌ Validation for blank input, duplicate IDs, and wrong login

---

## 🛠️ Built With

- **Java** (JDK 21)
- **Swing GUI** (via IntelliJ Designer)
- **IntelliJ IDEA**
- File handling with `BufferedReader` and `FileWriter`

---

## 📁 Project Structure
```
GradeManagementSystem/
├── src/ # All Java source and .form GUI files
│ ├── Main.java
│ ├── LoginWindow.java
│ ├── Registration.java
│ └── ...
├── Login.txt # Stores login usernames and passwords
├── studentdetails/ # Saved student data files
├── studenttranscript/ # Generated student transcripts
├── .gitignore # Ignore IDE files, compiled code, etc.
└── LICENSE # MIT License for open-source usage
└── README.md # This file
```
---

## ▶️ How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/anake-an/GradeManagementSystem.git
2. Open it in IntelliJ IDEA
3. Make sure Java SDK 21+ is set
4. Run Main.java to launch the app
5. Login using credentials from Login.txt:
     Username: Admin
     Password: Admin123

## 👥 Contributors

This project was originally developed as a group assignment for the KP14203 Object-Oriented Programming course.

| Name                                   | Profile Link                                                            |
|----------------------------------------|-------------------------------------------------------------------------|
| **Aniq Najmuddin Bin Sharifuddin**     | [Linkedin](https://www.linkedin.com/in/aniqnaj)                         |
| Muhammad Faris Bin Huzaimi             | [LinkedIn](https://www.linkedin.com/in/farishuz)                        |
| Muhammad Isyraf Ahnaf Bin M. Zamri     | [LinkedIn](https://www.linkedin.com/in/muhammad-isyraf-ahnaf-320557256) |
| Muhammad Saifullah Bin Rosman          |                                                                         |


