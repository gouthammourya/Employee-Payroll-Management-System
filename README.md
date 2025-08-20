# Employee Payroll Management System

## 📌 Overview
The **Employee Payroll Management System** is a Java-based desktop application that allows organizations to manage employee records, salary processing, and payslip generation. It uses **Java Swing** for the user interface and **MySQL** as the backend database. The system also supports **email notifications with PDF payslips**.

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
<img width="485" height="369" alt="Image" src="https://github.com/user-attachments/assets/acbc7688-59a4-4bbf-95cb-efa9950d42f0" />
<img width="494" height="371" alt="Image" src="https://github.com/user-attachments/assets/d89df0c2-4ff1-40f2-9dea-9df58fcc53ed" />
<img width="607" height="486" alt="Image" src="https://github.com/user-attachments/assets/340a1aea-a390-466f-af57-fb9f140dbfe5" />
<img width="732" height="863" alt="Image" src="https://github.com/user-attachments/assets/7a2c537e-a684-4deb-a332-747b43c1960d" />
<img width="731" height="861" alt="Image" src="https://github.com/user-attachments/assets/6f458cde-3c47-403a-b94b-067ada051715" />
<img width="985" height="742" alt="Image" src="https://github.com/user-attachments/assets/559bab7d-de83-4bb1-baed-c4d73656a7aa" />
<img width="1232" height="737" alt="Image" src="https://github.com/user-attachments/assets/13ee8586-0f97-4840-b382-704dcc5258fc" />
<img width="986" height="736" alt="Image" src="https://github.com/user-attachments/assets/7a5171e0-b61a-4694-93fa-17c7300fb290" />
<img width="976" height="738" alt="Image" src="https://github.com/user-attachments/assets/8d187f94-0552-4ba6-aad9-cd77969a47ce" />
<img width="1183" height="762" alt="Image" src="https://github.com/user-attachments/assets/27026b21-9deb-4d08-a152-8cd4b432eb8f" />

---

## 🚀 Future Enhancements
- Role-based authentication (Admin, HR, Accountant)  
- Cloud-based deployment  
- Web & Mobile versions  

---

## 👨‍💻 Author
Developed by Goutham Mourya A M  
