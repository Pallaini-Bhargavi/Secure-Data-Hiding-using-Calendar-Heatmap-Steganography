# 🔐 Secure Data Hiding using Calendar Heatmap Steganography

## 📌 Project Overview

This project implements a **secure data hiding and authentication system** using **Calendar Heatmap Steganography** combined with **strong user authentication and admin-controlled password reset workflows**. The system is designed with multiple security layers to prevent unauthorized access, brute-force attacks, and misuse of password reset functionality.

The application is built using **Spring Boot**, **Thymeleaf**, **JPA/Hibernate**, and **MySQL**, and follows clean MVC architecture.

---

## 🏗️ System Architecture

* **Frontend**: HTML, CSS, JavaScript, Thymeleaf
* **Backend**: Spring Boot (REST + MVC Controllers)
* **Database**: MySQL
* **Security**:

  * Password hashing (BCrypt)
  * Session-based authentication
  * Multi-level account locking
  * Admin approval-based password change

---

## 👥 User Roles

### 1️⃣ User

* Register and login securely
* Encode and decode messages using calendar heatmaps
* Request password reset (admin approval required)

### 2️⃣ Admin

* Login with restricted access
* View registered users
* Approve or reject password reset requests
* Monitor reset password logs

---

## 🔐 Security Flow Design

The system implements **three independent security flows**, each with its own attempts counter and lock mechanism.

### 🔹 1. Login Security (User Table)

| Feature    | Description                                         |
| ---------- | --------------------------------------------------- |
| Attempts   | `loginAttempts`                                     |
| Lock Field | `loginLockedUntil`                                  |
| Rule       | Lock account for **48 hours** after 3 failed logins |

---

### 🔹 2. Reset Password (Security Question) – User Table

| Feature    | Description                                               |
| ---------- | --------------------------------------------------------- |
| Attempts   | `resetAttempts`                                           |
| Lock Field | `resetLockedUntil`                                        |
| Rule       | Lock reset feature for **48 hours** after 3 wrong answers |

---

### 🔹 3. Change Password (Admin Approval) – Password Reset Table

| Feature    | Description                                                 |
| ---------- | ----------------------------------------------------------- |
| Table      | `password_reset_requests`                                   |
| Attempts   | `attempts` (default = 0)                                    |
| Lock Field | `lockedUntil`                                               |
| Status     | `PENDING → APPROVED / REJECTED`                             |
| Rule       | Lock change-password after **3 admin actions** for 48 hours |

---

## 🔁 Password Reset Workflow

### 🧑 User Flow

1. User enters email, new password, and security answer
2. System validates security answer
3. If valid:

   * A **PENDING** reset request is created
   * Admin is notified via email
4. Duplicate pending requests are blocked

### 👨‍💼 Admin Flow

1. Admin views pending reset requests
2. Admin **approves or rejects** the request
3. System updates:

   * `status` (APPROVED / REJECTED)
   * `actionTakenAt` (admin action time)
   * `attempts`
4. User is notified via email

---

## 📊 Admin Dashboard Features

* Users Data Table
* Password Reset Logs
* Approval Requests with badge count
* Approved Requests section
* Rejected Requests section

> Note: Approved/Rejected sections update on page refresh (server-rendered via Thymeleaf).

---

## 🧩 Database Tables

### 👤 User Table (Key Fields)

* `userEmail`
* `passwordHash`
* `securityQuestion`
* `securityAnswerHash`
* `loginAttempts`
* `loginLockedUntil`
* `resetAttempts`
* `resetLockedUntil`

### 🔑 Password Reset Requests Table

* `email`
* `newPasswordHash`
* `status`
* `createdAt`
* `attempts`
* `lockedUntil`
* `actionTakenAt`

---

## 🚀 Technologies Used

* Java 17
* Spring Boot 3
* Spring Security (PasswordEncoder)
* Spring Data JPA
* Thymeleaf
* MySQL
* Maven

---

## ✅ Key Highlights

* Multi-layered security implementation
* Admin-controlled sensitive operations
* No duplicate or conflicting reset requests
* Clear audit trail for admin actions
* Robust locking mechanism to prevent abuse

---

## 📘 Conclusion

This project demonstrates a **secure, scalable, and well-structured authentication system** integrated with an innovative **Calendar Heatmap Steganography** concept. The layered security design ensures high protection against unauthorized access while maintaining usability and transparency.

---

🔐 *Secure by design. Controlled by logic. Built for reliability.*
