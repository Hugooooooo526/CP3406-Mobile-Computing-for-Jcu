# 🌱 FocusGarden - Student Focus & Time Management App

## 📖 Project Overview

**FocusGarden** is a Kotlin-based Android application built with Jetpack Compose and Material 3 design principles, specifically designed for the CP3406 Mobile Computing course at James Cook University Singapore.

The app helps students manage study time through Pomodoro techniques, self-reflection, progress tracking, group challenges, and AI-generated productivity summaries.

---

## ✨ Core Features

| Feature Module | Description |
|----------------|-------------|
| 🎯 **Focus Timer** | Pomodoro-style focus timer with customizable durations and break reminders |
| 📓 **Focus Journal** | Post-session emotion and learning note recording |
| 📊 **Dashboard** | Visual display of daily/weekly progress, streaks, academic/personal ratio |
| 🧑‍🤝‍🧑 **Heist Group** | 3-5 person group collaboration tracking with shared goals |
| 🤖 **AI Summary** | Auto-generated weekly/monthly study reports and personalized recommendations |

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Design System:** Material 3
- **Architecture Pattern:** MVVM (Model-View-ViewModel)
- **Database:** Room Persistence Library
- **Navigation:** Jetpack Navigation Compose
- **Async:** Kotlin Coroutines + Flow
- **Dependency Injection:** Hilt (optional)

---

## 📂 Project Structure

```
CP3406-Mobile-Computing-for-Jcu/
├── docs/                              # Project documentation
│   └── TechnicalDevelopmentDocument.md
├── app/
│   └── src/main/
│       ├── java/com/jcu/focusgarden/
│       │   ├── ui/                    # UI layer
│       │   │   ├── screens/           # Screen composables
│       │   │   ├── components/        # Reusable components
│       │   │   └── theme/             # Material 3 theme
│       │   ├── data/                  # Data layer
│       │   │   ├── local/             # Room database
│       │   │   └── repository/        # Repository implementation
│       │   ├── domain/                # Business logic
│       │   └── viewmodel/             # ViewModels
│       └── res/                       # Resources
└── README.md
```

---

## 🎨 Design Specifications

### Color Scheme

| Color | HEX | Usage |
|-------|-----|-------|
| Primary Green | `#2E7D32` | Main buttons, accent color |
| Light Green | `#E8F5E9` | TopAppBar background, card emphasis |
| Progress Green | `#43A047` → `#81C784` | Progress bars, chart gradients |
| Academic Blue | `#1976D2` | Academic category indicator |
| Personal Orange | `#FF6F00` | Personal category indicator |
| Surface White | `#FFFFFF` | Card background |

### UI Principles

- **Minimalism:** White background + green accents, conveying calm and focus
- **Rounded Design:** Cards and buttons use 16dp corner radius
- **Soft Shadows:** Material 3 elevation system
- **Accessibility:** All touch targets ≥ 48dp, high contrast mode support

---

## 🚀 Quick Start

### Environment Requirements

- Android Studio Hedgehog | 2023.1.1 or higher
- Kotlin 1.9.0+
- Minimum SDK: 26 (Android 8.0)
- Target SDK: 34 (Android 14)

### Running Steps

1. **Clone the repository**
```bash
git clone https://github.com/Hugooooooo526/CP3406-Mobile-Computing-for-Jcu.git
cd CP3406-Mobile-Computing-for-Jcu
```

2. **Open project in Android Studio**

3. **Sync Gradle dependencies**

4. **Run the app**
   - Connect Android device or launch emulator
   - Click Run ▶️ button

---

## 📅 Development Timeline

| Week | Phase | Status |
|------|-------|--------|
| Week 1-2 | Project Setup & UI Theme | ✅ Completed |
| Week 3-4 | UI Wireframes & Static Layouts | ✅ Completed |
| Week 5-6 | MVP Development (Timer + Journal) | ⏳ Next Phase |
| Week 7-8 | Dashboard & Heist Features | ⏳ Planned |
| Week 9 | AI Summary Module | ⏳ Planned |
| Week 10 | Testing & Release | ⏳ Planned |

**Latest Update (2025-11-03):** Week 3-4 完成！所有 UI 界面、导航系统、Room 数据库架构已实现。详见 [Week 3-4 Progress Report](./docs/Week3-4_Progress_Report.md)

---

## 📚 Documentation

For detailed technical development documentation, please see:
- [Technical Development Document (TD)](./docs/TechnicalDevelopmentDocument.md)
- [Week 3-4 Progress Report](./docs/Week3-4_Progress_Report.md) - Latest development progress

---

## 👨‍💻 Developer

**Course:** CP3406 - Mobile Computing  
**Institution:** James Cook University Singapore  
**Developer:** Cui Langxuan (Hugo)  
**Student ID:** 14706438  
**Email:** langxuan.cui@my.jcu.edu.au

---

## 👨‍🏫 Lecturer Information

- **Lecturer GitHub:** [@Lum-KumMeng](https://github.com/Lum-KumMeng)
- **Email:** maxlum78@hotmail.com

---

## 📄 License

This project is for educational purposes only and is part of the JCU CP3406 course assignment.

---

## 🌿 Project Philosophy

> "Consistency grows your garden."  
> Through continuous focus and effort, cultivate good study habits and let your garden of knowledge flourish.
