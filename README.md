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