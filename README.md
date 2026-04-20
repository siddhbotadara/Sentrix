# Sentrix — Smart Autonomous Disaster Management System

## 🚀 Overview
Sentrix is a real-time, intelligent disaster management system designed to detect, analyze, and respond to multiple types of disasters automatically.

The system simulates a national-level emergency infrastructure that continuously monitors environmental data and triggers appropriate response mechanisms.

---

## 🌍 Problem
Disaster management systems are often:
- Reactive instead of proactive
- Slow in detecting critical events
- Inefficient in coordinating responses

This leads to increased damage, delayed emergency actions, and higher risk to human life.

---

## 💡 Solution
Sentrix provides:
- Real-time monitoring using simulated sensors
- Automatic disaster detection based on thresholds
- Intelligent response selection
- Instant alert broadcasting to multiple clients

---

## ⚙️ Features

### 📡 Monitoring
- Continuous sensor data updates
- Multi-region tracking system

### 🚨 Detection
- Flood, Tsunami, Earthquake, Typhoon, Heatwave detection
- Threshold-based alert triggering

### 🧠 Smart Response
- Dynamic response strategies based on disaster type
- Automated emergency actions

### 📢 Communication
- Alert broadcasting system (Socket-based)
- Simulated SMS alerts

### 🖥️ Visualization
- Color-coded GUI (Green / Yellow / Red)
- Real-time alert logs

---

## 🛠️ Requirements

Make sure you have the following installed:

- Java JDK 8 or higher  
- Apache Maven  

### Install Maven
Download from: https://maven.apache.org/download.cgi  

Verify installation:
mvn -v

---
## ⚙️ Setup Instructions

### 1. Clone the repository
- git clone https://github.com/siddhbotadara/Sentrix.git

- cd Sentrix

### 2. Create `.env` file

- In the root directory, create a file named:

  .env


- Add your API key:

  WEATHER_API_KEY=your_openweathermap_api_key


Get a free API key from:
https://openweathermap.org/ or contact owner

---

### 3. Install dependencies

- mvn clean install


---

### 4. Run the application

- mvn exec:java


---

## 🧩 System Architecture

Sensors → Weather Station → Alert System → Response Engine → Command Center → Clients (GUI)

---

## 🧠 Design Patterns Used

- **Observer Pattern**  
  Real-time updates between system components

- **Strategy Pattern**  
  Dynamic disaster response handling

- **Factory Pattern**  
  Creation of sensor objects

- **Singleton Pattern**  
  Centralized command control

- **Decorator Pattern**  
  Dynamic alert enhancements

- **Composite Pattern**  
  Region → Province hierarchy

- **Iterator Pattern**  
  Structured data traversal

---

## ⚡ Technologies Used
- Java (Core OOP)
- Multithreading
- Socket Programming
- Java Swing (GUI)

---

## 🎬 Demo Scenario
Example:
- Tsunami detected near coastal region
- System analyzes severity
- Response strategy activated
- Alerts broadcasted
- GUI updates to critical state

---

## 🔮 Future Improvements
- AI-based disaster prediction
- Integration with real-world APIs
- Mobile app support
- Cloud-based deployment

---

## 👥 Team
- Siddh Botadara
- Team Member 2

---

## 📌 Note
This project is developed as part of an Object-Oriented Programming course to demonstrate real-world system design and design pattern usage.