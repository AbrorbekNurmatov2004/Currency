# 💱 Currency Bot — Automated Exchange Rate & Conversion System

An enterprise-ready Telegram bot system built with **Java 17** and **Spring Boot 3** that interacts with external financial APIs to deliver real-time exchange rates, advanced currency conversion, and portfolio tracking. The application utilizes a high-performance NoSQL database for rapid user state management and historic rate caching.

---

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| ⚙️ **Core** | Java 17, Spring Boot 3.x |
| 🤖 **Bot** | Telegram Bots Spring Boot Starter |
| 🗄️ **Database** | MongoDB (NoSQL) |
| 🔌 **Integration** | RestTemplate / WebClient (External API consumption) |
| 🧪 **Testing** | JUnit 5, Mockito |

---

## 🏗️ Architecture

```text
                       ┌─────────────────────────┐
                       │  External Currency API  │
                       └────────────▲────────────┘
                                    │ (RestTemplate)
                                    ▼
[ Telegram User ] ──➔  [ Telegram Bot Engine ] ──➔ [ Service Layer ] ──➔ [ MongoDB ]
```

🚀 Key Features

🤖 Core Telegram Bot Engine

💱 Real-Time Conversion – Live processing of global exchange rates backed by instant math evaluations on user inputs.
📊 Multi-Currency Monitoring – Native tracking capabilities for global fiat standards (USD, EUR, RUB, UZB) configured to cross-convert seamlessly.
⚙️ State-Machine Lifecycle – Robust tracking of intermediate user actions (UserState) ensuring smooth workflows during setting changes and historical lookups.
🌐 Localization – Full multi-language layout support (Language enum) seamlessly adapting buttons and localized alert frameworks dynamically.


🗄️ Backend & Data Resilience

🍃 Scalable NoSQL Storage – Utilizing MongoDB to efficiently query, store, and mutate volatile user preferences and sessions without standard relational overhead.
🔌 Resilient HTTP Client Architecture – Built-in failover capabilities utilizing robust RestTemplate configurations to fetch structured JSON data from external banking endpoints.
🎯 Centralized Error Layouts – Global unexpected flow management (GlobalExceptionHandler) to ensure the Telegram polling layer never crashes on third-party API response inconsistencies.


📐 Code Quality & Architecture Best Practices

🏗️ Low Coupling, High Cohesion – Strict boundary separation where Handler classes act as traffic controllers, routing payloads immediately out of the Telegram threads into specialized Services.
📦 Domain Modeling and DTO Separation – Prevents leaking third-party JSON formats deep into the software layout by abstracting external payloads into strict dto classes before processing.
🔒 Immutable Design Constants – Critical UI notifications, database boundaries, and system metrics are cleanly isolated inside static utilities (ErrorConstants, Utility) preventing runtime state contamination.

src/main/java
├── CurrancyBotApplication.java
├── ButtonMaker.java
├── ConverterBot.java
├── config/
│   ├── AppConfig.java
│   └── BotInitializer.java
├── exception/
│   └── GlobalExceptionHandler.java
├── handler/
│   ├── CallbackQueryHandler.java
│   └── MessageHandler.java
├── model/
│   ├── dto/
│   ├── enums/
│   │   ├── Currency.java
│   │   ├── Language.java
│   │   └── UserState.java
│   └── Member.java
├── repository/
│   └── TelegramRepository.java
├── service/
│   ├── RemoteApiService.java
│   └── TelegramService.java
└── utils/
├── ErrorConstants.java
└── Utility.java