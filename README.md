# 🚆 Railway Reservation System

A Java-based Railway Reservation System that manages train schedules, seat availability, passenger bookings, and real-time updates using MySQL and JDBC.

---
## 🔥 Highlights
- Real-time seat allocation system
- JDBC-based database integration
- Admin-controlled train management
- Scalable relational schema design
## 📌 Features

* 🚉 Train Management (Add/View Trains)
* 🛤️ Route & Station Management
* 👤 Passenger Registration
* 🎟️ Ticket Booking & Cancellation
* 💺 Real-time Seat Availability Update
* 🔐 Admin Login System
* 🔔 Notification System for Passengers
* ⏱️ Train Status & Delay Updates

---

## 🛠️ Tech Stack

* **Backend:** Java (JDK 24)
* **Database:** MySQL
* **Connectivity:** JDBC
* **IDE:** IntelliJ IDEA

---

## 📂 Project Structure

```
RailwayReservationSystem/
│
├── src/
│   ├── Main.java
│   ├── DBConnection.java
│   ├── BookingService.java
│   ├── TrainService.java
│   ├── PassengerService.java
│   ├── AdminService.java
│   ├── NotificationService.java
│
├── database.sql
├── README.md
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

```
git clone https://github.com/your-username/Railway-Reservation-System.git
cd Railway-Reservation-System
```

### 2️⃣ Setup Database

* Open MySQL Workbench
* Import `database.sql`
* Run:

```
USE railway_db;
```

### 3️⃣ Configure Database Connection

Update in `DBConnection.java`:

```java
String url = "jdbc:mysql://localhost:3306/railway_db";
String user = "root";
String password = "your_password";
```

### 4️⃣ Run Project

* Open in IntelliJ IDEA
* Run `Main.java`

---

## ▶️ How It Works

1. Admin can add trains, stations, and update train status
2. Users can:

   * View trains
   * Check seat availability
   * Book tickets
   * Cancel tickets
3. Seat availability updates automatically in real-time
4. Notifications are stored for passengers

---

## 📊 Database Design

* Normalized relational schema
* Primary & Foreign key relationships
* Tables:

  * trains
  * stations
  * routes
  * passengers
  * seats
  * bookings
  * train_status
  * notifications
  * admins

---

## 🚀 Future Improvements

* GUI using JavaFX / Web UI
* Payment Integration
* Email/SMS Notifications
* Seat auto-allocation algorithm

---

## 📌 Author

**Runisha Banik**

---

## ⭐ If you like this project

Give it a star on GitHub ⭐
