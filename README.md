# 🌳 GandhaSiri - Sandalwood Farm Management & Monitoring System

**GandhaSiri** is a state-of-the-art Android application designed to empower sandalwood farmers and plantation owners. By blending modern mobile technologies, geospatial mapping, and Artificial Intelligence, the app provides robust end-to-end tracking, monitoring, security, and data-driven management of sandalwood tree assets.

---

## ✨ Key Features

### 📊 Farm & Tree Management
- **Tree Registration:** Seamlessly register each tree in the plantation with rich metadata (growth metrics, age, species details).
- **Interactive Tree Mapping:** Utilize integrated Google Maps to visually plot, track, and monitor individual tree locations across the entire estate.
- **Visual Analytics:** Embedded data visualization graphs powered by MPAndroidChart for tracking growth indices over time.

### 🤖 Gemini AI Assistant
- Real-time, conversational agronomy expert powered by **Google Gemini AI** to answer cultivation queries, disease diagnostics, and management strategies instantly.

### 🛡️ Robust Authentication & Security
- Fully secure user authentication and authorization powered by **Firebase Authentication** (with Google Sign-In support).

### 🌍 Advanced Localization
- Built with accessibility in mind. Fully localized and supporting dual-language configuration:
  - 🇬🇧 **English**
  - 🇮🇳 **Kannada (ಕನ್ನಡ)**
- Integrates modern system-level dynamic language switching (`locales_config`).

### 🔔 Real-time Cloud Messaging
- Dynamic push notifications via **Firebase Cloud Messaging (FCM)** to deliver immediate farm alerts, weather warnings, and management notifications.

### 🎨 Premium Modern Experience
- **Material 3 & Jetpack Compose UI:** Sleek, fully-native, fluid user interface.
- **Dark Mode Support:** Seamless toggle and persistence of light/dark themes using **Jetpack DataStore**.
- **Fluid Animations:** Integrated with Lottie for delightful user interactions.

---

## 🛠️ Technology Stack & Architecture

The architecture of GandhaSiri is built following strict **Clean Architecture principles** with standard **MVVM (Model-View-ViewModel)** structure, and layered boundaries (Domain, Data, Presentation).

### Core Tech
- **Language:** Kotlin (100%)
- **UI Framework:** Jetpack Compose (declarative UI) with Material 3 design tokens
- **Asynchronous API:** Kotlin Coroutines & Flow
- **Dependency Injection:** Dagger-Hilt

### Android Jetpack
- **DataStore:** Persistent user preference management (theme choice, localized state)
- **Room DB:** High-performance local offline database caching
- **Navigation Compose:** Fully type-safe declarative Compose routing
- **Lifecycle:** Lifecycle-aware ViewModel components

### Backend & APIs
- **Firebase SDK:** Authentication, Cloud Firestore (real-time database), Cloud Storage, Cloud Messaging (FCM)
- **Networking:** Retrofit & OkHttp with GSON serialization
- **Location:** Google Play Services Location & Maps Compose library

---

## 📂 Project Structure Highlights

```
com.gandhasiri.app
├── data                  # Data Sources & Repositories Implementation
│   └── repository
├── di                    # Dependency Injection Modules (Hilt)
├── domain                # Domain Business Logic
│   ├── model             # Data Models / Entities
│   └── repository        # Repository Abstraction Interfaces
├── navigation            # App Navigation Routes & Graph
├── presentation          # UI Layer (Jetpack Compose Screens & ViewModels)
│   ├── ai                # Gemini AI Chat screen
│   ├── auth              # Login & Registration screens
│   ├── home              # Main Dashboard & Maps
│   ├── profile           # User Profile settings (Theme & Language)
│   └── tree_register     # Tree mapping inputs
├── service               # System Services (FCM FirebaseMessagingService)
└── ui.theme              # Color Palette, Typography, and Custom App Theme
```

---

## 🚀 Getting Started

### 1. Prerequisites
- Android Studio Iguana / Ladybug (or newer)
- JDK 17 installed
- A Firebase Project with Firestore and Storage configured

### 2. Clone & Setup
1. Clone the repository to your local drive:
   ```bash
   git clone https://github.com/chethan7975/GandhaSiri.git
   ```
2. Open the project in **Android Studio**.
3. Drop your generated `google-services.json` from the Firebase Console into the `app/` directory of the project.
4. Synchroize your gradle project (`Sync Project with Gradle Files`).
5. Hit **Run** to build and launch onto your Android emulator or physical device.

---

## 📄 License

All code within this repository belongs to the creator. Modifications and redistribution should comply with repository-owner terms.

*Made with ❤️ for Smart Sandalwood Farming.*
