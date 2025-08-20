# Employee Payroll System

## 📌 Overview
The **Employee Payroll System** is a Java-based desktop application that allows organizations to manage employee records, salary processing, and payslip generation. It uses **Java Swing** for the user interface and **MySQL** as the backend database. The system also supports **email notifications with PDF payslips**.

---

## ✨ Features
- 👨‍💼 Add, edit, and remove employees  
- 📋 View and manage employee list  
- 💰 Calculate and process salaries with deductions (PF, ESI, etc.)  
- 📨 Send payment slips via email (PDF format)  
- 🔑 Secure login system for admin access  
- 🖥️ User-friendly Java Swing GUI  

---

## 🗂️ Project Structure
```
EmployeePayrollSystem/
│── src/miniproject/
│   ├── AddEmployee.java
│   ├── EditEmployee.java
│   ├── ManageEmployees.java
│   ├── EmployeeList.java
│   ├── Dashboard.java
│   ├── PaymentManagement.java
│   ├── EmailSender.java
│   ├── DBConnection.java
│   └── Login.java
│
│── bin/miniproject/        # Compiled .class files
│── .classpath              # Eclipse config
│── .project                # Eclipse project file
│── .settings/              # IDE settings
```

---

## 🛠️ Technologies Used
- **Java (JDK 8+)**
- **Swing (Java GUI Framework)**
- **MySQL Database**
- **JavaMail API** (for email sending)
- **iText PDF** (for payslip generation)

---

## ⚙️ Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/your-username/employee-payroll-system.git
cd employee-payroll-system/EmployeePayrollSystem
```

### 2. Configure Database
1. Install MySQL and create a database:
   ```sql
   CREATE DATABASE payroll_system;
   ```
2. Create `employees` and related tables (schema provided in `/database/schema.sql` if available).
3. Update **DBConnection.java** with your MySQL credentials:
   ```java
   String url = "jdbc:mysql://localhost:3306/payroll_system";
   String user = "your_mysql_user";
   String password = "your_mysql_password";
   ```

### 3. Dependencies
- Add required JAR files to your `lib/` folder or configure via your IDE:
  - `mysql-connector-java`
  - `javax.mail`
  - `activation`
  - `itextpdf`

### 4. Run the Application
- Open the project in **Eclipse/IntelliJ**
- Compile and run `Dashboard.java` or `Login.java`

---

## 📸 Screenshots
*(Add GUI screenshots here if available)*

---

## 🚀 Future Enhancements
- Role-based authentication (Admin, HR, Accountant)  
- Cloud-based deployment  
- Web & Mobile versions  

---

## 👨‍💻 Author
Developed by Goutham Mourya A M 
MCA Mini project – Employee Payroll Management System  
