# 🧩 FocusGarden – Technical Development Document (TD)

**Course:** CP3406 – Mobile Computing  
**App Title:** FocusGarden 🌱  
**Scenario:** #6 – Student Study & Time Management  
**Developer:** Cui Langxuan (Hugo)  
**Student ID:** 14706438  
**Campus:** James Cook University Singapore  
**Supervisor:** Lum Kum Meng (@Lum-KumMeng)  
**Date:** October 2025

---

## 1. 🏗️ Overview

FocusGarden is a Kotlin-based Android application built using Jetpack Compose and Material 3 design principles. It helps students manage study time, self-reflection, progress tracking, group motivation, and AI-generated productivity summaries.

The system integrates five major modules:

1. **Focus Timer** (Pomodoro System)
2. **Focus Journal** (Reflection Input)
3. **Dashboard** (Data Visualization)
4. **Heist Group Challenge** (Small Team Tracking)
5. **AI Summary Module** (Weekly/Monthly Reports + Recommendations)

---

## 2. 🧠 Architecture Overview

### 2.1 Architectural Pattern

The app follows the **MVVM (Model–View–ViewModel)** architecture pattern to ensure separation of concerns, scalability, and maintainability.

```
UI (Jetpack Compose Screens)
   ↓
ViewModel Layer (State Management, LiveData, UI Logic)
   ↓
Repository Layer (Abstraction of data sources)
   ↓
Room Database (Local data persistence)
   ↓
AI Module (Summary Generator, optional lightweight API)
```

### 2.2 Core Libraries & Tools

| Category | Tool / Library | Purpose |
|----------|---------------|---------|
| Programming Language | Kotlin | Primary development language |
| UI Framework | Jetpack Compose | Declarative UI |
| Design System | Material 3 | Theming, typography, icons |
| Database | Room Persistence Library | Local data storage |
| Dependency Injection | Hilt (optional) | ViewModel injection |
| Background Tasks | WorkManager / Foreground Service | Timer + Notifications |
| Visualization | Compose Canvas / Charts | Dashboard graphs |
| AI Summaries | Local ML logic / external API wrapper | Weekly/Monthly reports |
| Version Control | Git + GitHub | Branching + collaboration |
| Testing | JUnit + Espresso (optional) | Unit & UI testing |

---

## 3. ⚙️ Feature Design & Implementation Plan

| Feature | Description | Components | Data Model |
|---------|-------------|------------|------------|
| Pomodoro Timer | Start/Stop/Reset countdown with focus & break durations | ViewModel, CountDownTimer, Canvas, FloatingActionButton | `SessionEntity(id, startTime, duration, category)` |
| Focus Journal | Post-session popup to record emotion + notes | Dialog, TextField, AssistChip | `JournalEntity(sessionId, mood, note, date)` |
| Dashboard | Visual display of total focus time, streaks, academic/personal ratio | Card, Row, Canvas, BarChart | Aggregated from `SessionEntity` |
| Heist Group Challenge | Group of 3–5 users with shared goals, progress tracking | LazyColumn, LinearProgressIndicator, ListItem | `GroupEntity(groupId, name, members, streak, goal)` |
| AI Summary (Simplified) | Auto-generate weekly/monthly report + next-step recommendations | Card, Column, local summarization logic | Reads from `SessionEntity`, outputs JSON/text summary |

---

## 4. 🧩 UI / UX Layout Overview

### 4.1 Main Navigation Flow

```
Splash → Dashboard → Focus Timer → Journal Popup → Dashboard
                        ↓
                   Heist Group
                        ↓
                 AI Summary Report
```

### 4.2 Screen Descriptions

| Screen | Purpose | Key Composables |
|--------|---------|-----------------|
| DashboardScreen | Stats overview & quick actions | Scaffold, TopAppBar, Card, Button, Canvas |
| TimerScreen | Focus timer, triggers reflection | Box, Canvas, FAB, Dialog, TextField |
| JournalDialog | Capture emotions & short notes | AlertDialog, AssistChip, TextField, Button |
| HeistScreen | Display group progress, invite users | LazyColumn, ListItem, Card, ProgressIndicator |
| AISummaryScreen | Display auto-generated reports + next-step suggestions | Card, Column, Row, Text, Button |

### 4.3 Detailed UI/UX Design Specifications

#### 🖼️ 4.3.1 Dashboard Screen – "Focus Overview"

**Purpose:**  
Display the user's daily and weekly progress, personal–academic workload balance, and quick navigation actions.

**Visual Summary:**  
A calm, bright home screen with white background and green accents. All elements are organized in vertically stacked cards with rounded corners and soft shadows. The design conveys a sense of focus and balance.

**Layout Structure:**

- **TopAppBar:**
  - Title: "FocusGarden 🌱"
  - Background: Light green (#E8F5E9)
  - Center-aligned title, no navigation drawer

- **Main Body (Column inside Scaffold):**

  1. **Card 1 – Today's Focus**
     - Header text: "Today's Focus" (20sp, bold)
     - Subtext: "Focused 75 min"
     - Right corner: small flame icon 🔥 with text "3-Day Streak"
     - Subtle circular progress indicator showing today's completion

  2. **Card 2 – Weekly Focus Progress**
     - Header: "This Week"
     - Mini horizontal bar chart showing 7 bars (Mon–Sun), each with green gradient (#43A047 → #81C784)
     - Each bar labeled below with day initials (M, T, W, …)

  3. **Card 3 – Workload Balance**
     - Header: "Workload Balance"
     - Donut chart (60% Academic, 40% Personal)
     - Two legends with small colored circles (blue for academic, orange for personal)

  4. **Card 4 – Quick Actions Row**
     - Three evenly spaced ElevatedButtons with icons and labels:
       - 🎯 Start Focus
       - 📓 View Journal
       - 🤖 AI Summary
     - Buttons are Material 3 ElevatedButtons, rounded 16dp, filled with accent green (#2E7D32)

  5. **Footer:** small motivational text "Consistency grows your garden 🌿"

**Component Map:**  
`Scaffold → TopAppBar, Column, multiple Card (each containing Row, Column, Canvas, Text, Button)`

---

#### ⏳ 4.3.2 Timer Screen – "Focus Session"

**Purpose:**  
Run the Pomodoro-style focus session, including a countdown timer, ambient sound toggle, and quick post-session reflection popup.

**Visual Summary:**  
Minimalist white screen with centered circular timer, green progress animation, and large typography. The design emphasizes calmness and simplicity.

**Layout Structure:**

- **TopAppBar:**
  - Title: "Focus Timer"
  - Background: light green (#E8F5E9)

- **Main Center Content:**
  - **Large Circular Countdown Timer:**
    - Canvas composable drawing circular ring (stroke width 12dp)
    - Inner text "25:00" (bold, 48sp)
    - Green progress arc (#4CAF50)
  
  - **Below:** two main buttons horizontally centered:
    - FloatingActionButton (green) → Start / Pause toggle
    - TextButton "Reset"

- **Bottom Row:**
  - Label: "Ambient Sound 🌿"
  - Material Switch toggle

- **Dialog (Triggered at End of Timer):**
  - Title: "Reflect on Your Session 🌱"
  - Mood Chips (row of emojis): 😀 🙂 😐 🙁
  - TextField (hint: "What did you learn or improve?")
  - Buttons:
    - Save (green)
    - Skip (grey)

**Color Palette:**  
White background, accent green (#2E7D32), soft shadowed FAB, Roboto font

**Component Map:**  
`Scaffold, Box, Canvas, FloatingActionButton, TextButton, Dialog, TextField, AssistChip`

---

#### 🧑‍🤝‍🧑 4.3.3 Heist Group Challenge Screen – "Team Focus"

**Purpose:**  
Encourage cooperative focus via small groups of 3–5 users tracking progress together.

**Visual Summary:**  
Friendly group screen using Material 3 neutral tones with green highlights. Each member's progress is shown as a horizontal bar. Cooperative atmosphere emphasized; no leaderboard.

**Layout Structure:**

- **TopAppBar:**
  - Title: "Heist Group"
  - Right icon: overflow menu (⋮) → options: Leave Group / Report

- **Group Goal Card (Top):**
  - Title: "Complete 8 Pomodoros Today"
  - Subtitle: "🔥 3-Day Group Streak"
  - Invite Member Button (Outlined, icon: ➕)
  - Light green background (#E8F5E9)

- **Member Progress List (LazyColumn):**  
  Each item includes:
  - Left: circular avatar (mock initials or icon)
  - Middle: name + today's minutes (e.g., "Alex – 60 min")
  - Bottom: LinearProgressIndicator (e.g., 0.6 progress, green)
  - Right: small thumbs-up icon button 👍
  - Divider between rows

- **Bottom Section:**
  - Green ElevatedButton: "Start Focus with Team"
  - Text below: "Small wins together make big growth 🌱"

**Color Palette:**  
White + pale yellow accent (#FFF8E1), accent green buttons (#43A047)

**Component Map:**  
`Scaffold, TopAppBar, Card, LazyColumn, ListItem, LinearProgressIndicator, IconButton, Button`

---

#### 🤖 4.3.4 AI Summary Screen – "Weekly Insights"

**Purpose:**  
Generate auto summaries of focus data and actionable recommendations without requiring user input.

**Visual Summary:**  
Clean, structured dashboard with data cards and recommendation list. Calm, analytical layout inspired by Google Fit/Wellbeing visuals.

**Layout Structure:**

- **TopAppBar:** "AI Summary"

- **Main Scrollable Column:**

  1. **Card – Weekly Summary:**
     - Header: "Weekly Summary"
     - Key stats in a 2×2 grid:
       - Total Focus Time → "540 min"
       - Average per Day → "77 min"
       - Longest Streak → "5 Days"
       - Peak Day → "Wednesday"
     - Below grid: small line chart showing productivity trend (Canvas)
     - Mood trend icons (🙂 → 😀)

  2. **Card – Next Week Recommendations:**
     - Header: "Next Week Recommendations"
     - Three bullet points (use ListItem):
       - "Schedule morning sessions."
       - "Shorter breaks on Wednesday."
       - "Maintain weekend rest balance."

  3. **CTA Button (Bottom):**
     - Text: "Generate Monthly Report (PDF)"
     - Green ElevatedButton centered

**Color Palette:**  
White base, green accent (#2E7D32), calm typography, subtle shadows

**Important: No Chat Elements** - No user input, no chat bubbles or avatar

**Component Map:**  
`Scaffold, Card, Column, Row, Text, Button, Canvas, ListItem`

---

### 4.4 UI Implementation Technical Points

- **Framework:** Jetpack Compose + Material 3
- **Typography:** MaterialTheme.typography.titleMedium / bodyLarge
- **Color System:** MaterialTheme.colorScheme with primary = #2E7D32
- **Navigation:** NavHostController with routes: /dashboard, /timer, /heist, /ai_summary
- **State Management:** ViewModel + LiveData for all data-bound UI (Room sync)
- **Charts:** Lightweight custom Canvas charts for performance
- **Accessibility:** High-contrast mode, content descriptions for icons, minimum touch target 48dp

---

## 5. 🧮 Data Model (Room Entities)

### SessionEntity

```kotlin
@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val duration: Int,
    val startTime: Long,
    val endTime: Long,
    val date: String
)
```

### JournalEntity

```kotlin
@Entity(tableName = "journals")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sessionId: Int,
    val mood: String,
    val note: String,
    val date: String
)
```

### GroupEntity

```kotlin
@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey val groupId: String,
    val name: String,
    val members: List<String>,
    val streak: Int,
    val goal: String
)
```

---

## 6. 🧠 AI Summary Module (Simplified Implementation)

### Goal

Generate weekly/monthly summaries and next-step recommendations using stored focus data.

### Local Algorithm (No live chat)

```kotlin
fun generateWeeklySummary(sessions: List<SessionEntity>): Summary {
    val totalTime = sessions.sumOf { it.duration }
    val avgDaily = totalTime / 7
    val peakDay = sessions.groupBy { it.date }.maxByOrNull { it.value.size }?.key
    val recommendation = when {
        avgDaily < 90 -> "Try adding one more session per day next week."
        else -> "Maintain your current routine. Great job!"
    }
    return Summary(totalTime, avgDaily, peakDay ?: "", recommendation)
}
```

### Output Example

```
Weekly Summary:
- Total Focus Time: 540 mins
- Peak Day: Wednesday
- Average Daily Focus: 77 mins

Next Step:
- Increase Friday sessions by 15 mins to balance workload.
```

---

## 7. 🧭 Development Timeline (Week 1–10)

| Week | Phase | Key Tasks | Deliverables |
|------|-------|-----------|--------------|
| Week 1–2 | Setup & Research | - Android Studio setup<br>- GitHub repo init<br>- Jetpack Compose tutorials<br>- Confirm UI theme/colors | Project skeleton + theme prototype |
| Week 3–4 | UI Wireframes | - Build static UI layouts<br>- Dashboard, Timer, Heist mockups | Compose layouts, no logic |
| Week 5–6 | MVP Development | - Timer logic + Room DB<br>- Journal popup<br>- Data persistence | Fully working Timer & Journal |
| Week 7–8 | Feature Integration | - Dashboard data visualization<br>- Heist group mock data<br>- Local storage aggregation | Working Dashboard + Group view |
| Week 9 | AI Summary Module | - Local summary generation logic<br>- Report screen UI<br>- Recommendation engine | Working AI Summary feature |
| Week 10 | Testing & Polish | - Unit tests + UI polish<br>- Bug fixes, refactor<br>- Prepare presentation & APK | Stable build + recorded demo |

---

## 8. 🧰 GitHub & Version Control Strategy

**Repository:**  
`https://github.com/Hugooooooo526/CP3406-Mobile-Computing-for-Jcu`

### Branching Model

```
main     → Stable releases
dev      → Integrated testing branch
feature/timer
feature/journal
feature/dashboard
feature/heist
feature/ai_summary
```

### Commit Convention

```
feat(timer): implement basic countdown
fix(db): corrected Room entity mapping
chore(ui): update color scheme
```

---

## 9. 🔍 Testing Plan

| Test Type | Description | Tool |
|-----------|-------------|------|
| Unit Tests | Validate ViewModel logic, Timer calculations | JUnit |
| UI Tests | Check UI rendering and navigation | Compose Test |
| Integration Tests | Verify DB read/write and AI summary generation | JUnit + Mock Data |
| Manual Tests | Run on emulator/device to check timing accuracy | Android Studio Emulator |

---

## 10. ⚠️ Risk Management

| Risk | Impact | Mitigation |
|------|--------|------------|
| Timer pauses on background | Medium | Use Foreground Service for timer |
| AI logic performance | Low | Limit dataset, summarize locally |
| Group sync complexity | Medium | Start with mock local data only |
| Scope creep | High | Focus on MVP + simple AI summaries |
| Testing delay | Medium | Begin manual testing from Week 8 |

---

## 11. 🧾 Future Improvements (Post-Project)

- Cloud sync (Firebase) for Heist groups
- Deeper AI analytics using OpenAI or Gemini APIs
- Multi-device streak synchronization
- Dark/light adaptive themes
- Export focus data to calendar

---

## ✅ Summary

This Technical Development Document (TD):
- Follows the confirmed Scenario 6 + Week 1–10 plan
- Includes AI Summary simplification (auto summaries + next-step advice)
- Defines realistic Compose architecture & development flow
- Ready for actual implementation and submission in CP3406 Part B–C
