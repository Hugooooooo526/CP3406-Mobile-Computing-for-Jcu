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
  - Center-aligned title
  - Right actions: 
    - Theme toggle button (🌓 icon)
    - Music control button (🎵 icon, changes to ⏹️ when playing)

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

  4. **Footer:** small motivational text "Consistency grows your garden 🌿"

**Component Map:**  
`Scaffold → TopAppBar (with theme + music buttons), Column, multiple Card (each containing Row, Column, Canvas, Text)`

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

| Week | Phase | Key Tasks | Deliverables | Status |
|------|-------|-----------|--------------|--------|
| Week 1–2 | Setup & Research | - Android Studio setup<br>- GitHub repo init<br>- Jetpack Compose tutorials<br>- Confirm UI theme/colors | Project skeleton + theme prototype | ✅ Completed |
| Week 3–4 | UI Wireframes & Architecture | - Build static UI layouts<br>- Dashboard, Timer, Heist, AI mockups<br>- Room database setup<br>- MVVM structure | Compose layouts + Database schema | ✅ Completed |
| Week 5–6 | **MVP Development** | **详见 Section 7.1** | **Fully working Timer & Journal** | ✅ **Completed (2025-11-10)** |
| Week 7–8 | Feature Integration | **详见 Section 7.2**<br>- ✅ Dashboard data visualization<br>- ✅ Heist group mock data | Working Dashboard + Heist Group | ✅ **Completed (2025-11-10)** |
| Week 9 | AI Summary Module | - Local summary generation logic<br>- Report screen UI<br>- Recommendation engine | Working AI Summary feature | ⏳ Next Phase |
| Week 10 | Testing & Polish | - Unit tests + UI polish<br>- Bug fixes, refactor<br>- Prepare presentation & APK | Stable build + recorded demo | ⏳ Planned |

---

### 7.1 Week 5-6 MVP Development (详细任务分解)

**目标:** 实现完整的 Timer → Journal 工作流 + 数据持久化

**开发日期:** 2025-11-07 开始  
**预计完成:** Week 6 结束

#### 技术决策（已确认）

1. **Timer 实现方式**
   - ✅ 使用 Kotlin Flow + delay（前台计时）
   - ❌ 不使用 Foreground Service（保持简单）
   - **理由:** MVP 阶段只需前台功能，降低复杂度

2. **Category 分类选择**
   - ✅ Timer 完成后，在 Journal 反思对话框选择
   - 选项: Academic（学术）/ Personal（个人）
   - UI: RadioButton 或 SegmentedButton

3. **开发范围**
   - ✅ Week 5-6: Timer + Journal + Session/Journal 数据持久化
   - ✅ Week 7-8: Dashboard 数据可视化（从数据库读取）
   - ✅ Week 7-8: Heist 小组功能
   - ✅ Week 9: AI Summary 功能

#### Phase A: Timer 倒计时核心逻辑 (2-3h)

**任务清单:**
- [x] A1: 实现 Flow-based 倒计时逻辑
- [x] A2: 集成到 TimerViewModel（toggleTimer, resetTimer）
- [x] A3: 连接 TimerScreen 与 ViewModel 状态
- [x] **Bug 修复 (2025-11-10):** 修正 toggleTimer 状态更新顺序

**实现代码框架:**
```kotlin
// TimerViewModel.kt
private var timerJob: Job? = null

private fun startCountdown() {
    timerJob = viewModelScope.launch {
        while (_remainingSeconds.value > 0 && _isRunning.value) {
            delay(1000L)
            _remainingSeconds.value -= 1
        }
        if (_remainingSeconds.value == 0) {
            onTimerComplete()
        }
    }
}
```

**文件:** `TimerViewModel.kt`, `TimerScreen.kt`

#### Phase B: Session 数据保存 (2-3h)

**任务清单:**
- [x] B1: 实现 Session 保存到 Room 数据库
- [x] B2: Timer 完成时创建并保存 SessionEntity
- [x] B3: 记录 startTime, duration, category

**实现代码框架:**
```kotlin
// TimerViewModel.kt
private var sessionStartTime: Long = 0L

fun completeSession(category: String) {
    viewModelScope.launch {
        val session = SessionEntity(
            startTime = sessionStartTime,
            duration = (25 * 60) - _remainingSeconds.value,
            category = category,
            date = System.currentTimeMillis()
        )
        currentSessionId = sessionRepository.insertSession(session)
        _showReflectionDialog.value = true
    }
}
```

**文件:** `TimerViewModel.kt`, `SessionRepository.kt`, `SessionDao.kt`

#### Phase C: Journal 反思对话框完善 (3-4h)

**任务清单:**
- [x] C1: 完善 ReflectionDialog UI
- [x] C2: 添加 Category 选择（Academic/Personal）
- [x] C3: 实现情绪选择（FilterChip）
- [x] C4: 实现学习笔记输入（TextField）

**UI 设计:**
```
┌────────────────────────────────────┐
│  Reflect on Your Session 🌱        │
├────────────────────────────────────┤
│  📚 Category:                      │
│  ⭕ Academic    ⚪ Personal        │
├────────────────────────────────────┤
│  😊 How do you feel?               │
│  [Productive] [Focused] [Tired]    │
├────────────────────────────────────┤
│  📝 What did you learn?            │
│  [Multi-line TextField]            │
├────────────────────────────────────┤
│  [Skip]              [Save]        │
└────────────────────────────────────┘
```

**文件:** `TimerScreen.kt`

#### Phase D: Journal 数据保存 (1-2h)

**任务清单:**
- [x] D1: 实现 Journal 保存（关联 sessionId）
- [x] D2: 处理 Save 和 Skip 按钮逻辑
- [x] D3: 更新 Session 的 category 字段

**实现代码框架:**
```kotlin
// TimerViewModel.kt
fun saveReflection(category: String, mood: String, note: String) {
    viewModelScope.launch {
        // 更新 Session category
        sessionRepository.updateSessionCategory(currentSessionId, category)
        
        // 保存 Journal
        val journal = JournalEntity(
            sessionId = currentSessionId,
            mood = mood,
            note = note,
            date = System.currentTimeMillis()
        )
        journalRepository.insertJournal(journal)
        
        _showReflectionDialog.value = false
        resetTimer()
    }
}
```

**文件:** `TimerViewModel.kt`, `JournalRepository.kt`, `JournalDao.kt`

#### Phase E: Dashboard 数据读取（基础） (3-4h) ✅ **已完成 (2025-11-10)**

**任务清单:**
- [x] E1: 从数据库读取今日专注时长
- [x] E2: 计算连续打卡天数（Streak）
- [x] E3: 计算本周数据（7天柱状图）
- [x] E4: 计算学术/个人占比（饼图）

**实现详情:**
- ✅ 添加新的 DAO 查询方法（getAllDistinctDates, getCategoryDurations）
- ✅ 更新 SessionRepository 暴露新查询
- ✅ 实现 DashboardViewModel 完整数据加载逻辑
- ✅ DashboardScreen 集成 ViewModel，显示真实数据

**实现代码框架:**
```kotlin
// DashboardViewModel.kt
private fun loadTodayFocusTime() {
    viewModelScope.launch {
        val today = getTodayStartTimestamp()
        val sessions = sessionRepository.getSessionsByDate(today)
        _todayFocusMinutes.value = sessions.sumOf { it.duration } / 60
    }
}

private suspend fun calculateStreak(): Int {
    // 按日期分组，检查连续天数
    // 从今天往前推，遇到空缺日期则中断
}
```

**文件:** `DashboardViewModel.kt`, `SessionRepository.kt`, `SessionDao.kt`

#### Phase F: Timer Duration Adjustment (0.5h) ✅ **已完成 (2025-11-10)**

**功能描述:** 允许用户在 Timer Screen 调节专注时长

**设计规格:**
- 时间范围: 5-60 分钟，步长 5 分钟（5, 10, 15, ..., 60）
- UI组件: Material 3 Slider（滑块）
- UI位置: 圆形计时器下方
- 交互限制: 仅在 Timer 未运行时可调节（运行中禁用）
- 默认值: 25 分钟（Pomodoro 标准）
- 数据持久化: 不记住上次选择（保持简单）

**UI布局:**
```
    ┌─────────┐
    │  25:00  │  ← 圆形计时器
    └─────────┘
    
    Focus Duration
  ━━━━━●━━━━━━━  25 min  ← Slider
  5 min        60 min
  
   [▶️] [🔄]    ← 控制按钮
```

**实现清单:**
- [x] TimerViewModel 添加 `focusDuration` StateFlow
- [x] 添加 `setFocusDuration()` 方法
- [x] 修改 `resetTimer()` 使用自定义时长
- [x] TimerScreen 添加 Slider UI
- [x] Slider 在运行中禁用

**代码框架:**
```kotlin
// TimerViewModel.kt
private val _focusDuration = MutableStateFlow(25) // 默认25分钟
val focusDuration: StateFlow<Int> = _focusDuration.asStateFlow()

fun setFocusDuration(minutes: Int) {
    if (!_isRunning.value) {
        _focusDuration.value = minutes
        _remainingSeconds.value = minutes * 60
    }
}
```

**文件:** `TimerViewModel.kt`, `TimerScreen.kt`

---

#### Enhancement: Timer Skip Function ✅ **已完成 (2025-11-20)**

**功能描述:** 允许用户跳过倒计时，但统计原定专注时长

**用户需求:**
- 用户希望能提前结束计时
- 但仍然统计原定要专注的时间（而非实际经过时间）
- 跳过后仍可填写 Journal 和选择 Category

**设计决策:**
1. **跳过按钮显示时机:** 仅在计时器运行或已开始时显示
2. **统计逻辑:** 
   - 跳过时：保存 `_focusDuration` (原定时长)
   - 正常完成：保存实际经过时间
3. **UI 位置:** Skip 按钮放在 Start/Pause FAB 右侧
4. **音效反馈:** 跳过时播放完成音效

**实现内容:**

```kotlin
// TimerViewModel.kt - 新增 skipTimer() 方法
fun skipTimer() {
    if (!_isRunning.value && sessionStartTime == 0L) {
        return // 未开始不执行
    }
    _isRunning.value = false
    pauseTimer()
    _showReflectionDialog.value = true // 显示反思对话框
}

// 更新 saveReflection() 逻辑
val durationToSave = if (_remainingSeconds.value > 0) {
    _focusDuration.value  // 跳过：使用原定时长
} else {
    ((endTime - sessionStartTime) / 1000 / 60).toInt() // 正常：实际时长
}

// TimerScreen.kt - Skip Button UI
if (isRunning || remainingSeconds < focusDuration * 60) {
    FilledTonalButton(
        onClick = { 
            viewModel?.skipTimer()
            soundManager?.playComplete()
        }
    ) {
        Icon(Icons.Default.SkipNext)
        Text("Skip")
    }
}
```

**测试场景:**

| 场景 | 操作 | 预期结果 |
|------|------|----------|
| 跳过功能 | 25分钟计时，运行5分钟后点击Skip | 保存 duration=25 |
| 正常完成 | 25分钟计时，等待完成 | 保存 duration=25 |
| 调节后跳过 | 10分钟计时，运行2分钟后Skip | 保存 duration=10 |

**文件修改:**
- `viewmodel/TimerViewModel.kt` - 添加 skipTimer()，更新 saveReflection()
- `ui/screens/TimerScreen.kt` - 添加 Skip 按钮 UI

**开发时间:** 约 20 分钟

**影响范围:** 
- ✅ 不影响现有功能
- ✅ 向后兼容
- ✅ Dashboard 统计正确

---

### 7.2 Week 7-8 Feature Integration (Heist Group)

**目标:** 实现 Heist Group 小组协作功能（简化版）

**开发日期:** 2025-11-10  
**状态:** ✅ 已完成

#### 技术决策

1. **实现方式:** 简化版（模拟数据）
   - ✅ 使用静态模拟数据展示小组功能
   - ❌ 不连接数据库（保持简单）
   - **理由:** 快速展示功能概念，为后续扩展预留空间

2. **功能范围:**
   - ✅ 小组目标和 Streak 展示
   - ✅ 成员进度列表（4名成员）
   - ✅ 小组总体进度条
   - ❌ 实时数据同步（未实现）

#### 实现内容

**任务清单:**
- [x] 优化 HeistScreen UI 展示
- [x] 增强小组目标卡片（添加进度条）
- [x] 更新成员进度显示（今日分钟数 + 本周进度）
- [x] 调整 MemberProgress 数据结构
- [x] 美化 UI 细节和间距

**模拟数据结构:**
```kotlin
// 小组信息
val groupName = "Study Squad"
val groupGoal = "Complete 30 Pomodoros This Week"
val groupStreak = 5 // 连续 5 天
val totalCompleted = 18 // 已完成 18 个
val totalTarget = 30 // 目标 30 个

// 成员信息（4人）
val members = listOf(
    MemberProgress("Alex Chen", 75, 5, 8, "AC"),    // 今日75分钟，本周5/8完成
    MemberProgress("Sara Kim", 50, 4, 8, "SK"),     // 今日50分钟，本周4/8完成
    MemberProgress("John Davis", 100, 6, 8, "JD"),  // 今日100分钟，本周6/8完成
    MemberProgress("Emma Wilson", 45, 3, 8, "EW")   // 今日45分钟，本周3/8完成
)
```

**UI 增强:**
1. **小组目标卡片:**
   - 🎯 小组名称（emoji + 标题）
   - 📝 目标描述
   - 📊 团队进度条（18/30）
   - 🔥 小组 Streak 显示
   - ➕ 邀请成员按钮

2. **成员进度项:**
   - 👤 头像（缩写圆形）
   - 📊 姓名 + 今日分钟数
   - 📈 本周进度条（x/8）
   - 👍 点赞按钮

**文件:** `HeistScreen.kt` (449 行)

**开发时间:** 约 30 分钟

---

### 7.3 Week 9 Enhancements (Gemini API + PDF Export)

**目标:** 增强 AI Summary 功能，添加 Gemini API 集成和 PDF 导出

**开发日期:** 2025-11-13  
**状态:** ✅ 已完成

#### 技术决策

1. **Gemini API Integration**
   - ✅ 使用 Google Generative AI (gemini-pro model)
   - ✅ AI-powered insights generation
   - ✅ Configurable API key in GeminiConfig
   - **理由:** 提供个性化、智能的分析建议

2. **PDF Generation**
   - ✅ 使用 Android 原生 PdfDocument API
   - ❌ 不使用第三方 PDF 库（避免复杂依赖）
   - ✅ A4 size (595x842 points)
   - **理由:** 简单、可靠、无需外部依赖

3. **功能范围:**
   - ✅ 生成包含 AI insights 的 PDF 报告
   - ✅ 支持无 API key 情况（生成基础 PDF）
   - ✅ 错误处理完善
   - ✅ 状态实时反馈

#### 实现内容

**任务清单:**
- [x] 添加 Gemini API 依赖 (`build.gradle.kts`)
- [x] 创建 GeminiConfig 配置文件
- [x] 创建 GeminiService API 服务
- [x] 创建 PDFGenerator 工具类
- [x] 更新 AISummaryViewModel (添加 PDF 生成方法)
- [x] 更新 AISummaryScreen UI (添加导出按钮)
- [x] 修复底部导航栏跳转问题
- [x] 创建测试指南文档

**新增文件:**
1. `api/GeminiConfig.kt` - API 配置
2. `api/GeminiService.kt` - Gemini API 服务
3. `utils/PDFGenerator.kt` - PDF 生成工具
4. `docs/PDF_Generation_Test_Guide.md` - 测试指南

**修改文件:**
1. `build.gradle.kts` - 添加依赖
2. `viewmodel/AISummaryViewModel.kt` - PDF 生成逻辑
3. `ui/screens/AISummaryScreen.kt` - PDF 导出 UI
4. `ui/navigation/FocusGardenApp.kt` - 修复导航

**依赖添加:**
```kotlin
// Gemini API
implementation("com.google.ai.client.generativeai:generativeai:0.1.2")

// PDF generation
implementation("com.itextpdf:itext7-core:7.2.5")

// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

**Gemini API Prompt:**
```kotlin
You are a productivity coach analyzing a student's weekly focus data. 
Provide 2-3 sentences of insightful, actionable feedback.

Weekly Data:
- Total Focus Time: X minutes
- Average per Day: Y minutes
- Current Streak: Z days
- Peak Day: Monday
- Academic/Personal Time breakdown

Provide:
1. One specific strength they're showing
2. One actionable improvement suggestion
3. One motivational insight
```

**PDF Content Structure:**
```
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Weekly Focus Report
Generated: [Date]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Weekly Summary
• Total Focus Time
• Average per Day
• Current Streak
• Peak Day
• Total Sessions

Category Breakdown
• Academic Time
• Personal Time

Recommendations
[Priority indicators]
• Recommendation 1
• Recommendation 2
• ...

AI-Powered Insights (if API key configured)
[2-3 sentences of personalized feedback]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

**文件保存位置:**
```
/storage/emulated/0/Android/data/com.jcu.focusgarden/files/Documents/
FocusGarden_Report_[timestamp].pdf
```

**开发时间:** 约 2 小时

#### 总预计时间: 11-16 小时 (Week 5-6) + 0.5 小时 (Week 7-8) + 2 小时 (Week 9 Enhancement)

#### 端到端测试流程

```
1. 打开 Timer Screen
2. 点击 Start → 倒计时开始 (25:00 → 24:59 → ...)
3. 点击 Pause → 倒计时暂停
4. 点击 Resume → 继续倒计时
5. 倒计时结束 → 自动弹出 Journal 对话框
6. 选择 Category: Academic
7. 选择 Mood: Productive
8. 输入 Note: "Completed homework"
9. 点击 Save → 数据保存到数据库，对话框关闭
10. 返回 Dashboard → 显示真实数据
```

**详细开发计划文档:** [Week5-6_MVP_Development_Plan.md](./Week5-6_MVP_Development_Plan.md)

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

### Code Standards

**Language Policy (Enforced: 2025-11-10):**
- ✅ **All code comments must be in English**
- ✅ **All UI text and mock data must be in English**
- ✅ **Variable names, function names, class names must be in English**
- ✅ **Documentation can be bilingual (English + Chinese) for TD only**
- ❌ **No Chinese in source code (.kt, .java, .xml files)**

**Rationale:**
- International collaboration readiness
- Better code review and maintenance
- Professional development standards
- Easier for non-Chinese speakers to contribute

**Examples:**

✅ **Good (English):**
```kotlin
// Timer countdown core logic
fun startCountdown() {
    // Start the timer
}

val groupName = "Study Squad"  // Mock group data
```

❌ **Bad (Chinese):**
```kotlin
// 计时器倒计时核心逻辑
fun startCountdown() {
    // 开始计时
}

val groupName = "学习小组"  // 模拟小组数据
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

| Risk | Impact | Mitigation | Status |
|------|--------|------------|--------|
| Timer pauses on background | Low | ✅ **决策:** MVP 只需前台计时，降低复杂度 | ✅ Resolved |
| AI logic performance | Low | Limit dataset, summarize locally | ⏳ Monitoring |
| Group sync complexity | Medium | Start with mock local data only (Week 7-8) | ⏳ Planned |
| Scope creep | High | Focus on MVP + simple AI summaries<br>✅ 已确认: Week 5-6 只做 Timer + Journal | ✅ Controlled |
| Testing delay | Medium | Begin manual testing from Week 8 | ⏳ Planned |
| Database query performance | Low | Data volume small in MVP, impact minimal | ⏳ Monitoring |
| UI recomposition performance | Low | Compose auto-optimization, 1-sec updates acceptable | ⏳ Monitoring |

### 最新风险评估（Week 5-6）

**✅ 已解决的风险:**
1. **Timer 后台暂停问题**
   - 原方案: Foreground Service（复杂）
   - 新决策: 前台计时 + Kotlin Flow（简单）
   - 结果: 降低实现复杂度，符合 MVP 目标

2. **功能范围蔓延**
   - 明确 Week 5-6 任务: Timer + Journal + 数据持久化
   - Dashboard/Heist/AI 推迟到 Week 7-9
   - 已取消: 滚动时间选择器 + 正计时模式（不在 MVP 范围）

**⏳ 需监控的风险:**
- Timer 倒计时性能（每秒更新 UI）→ 使用 StateFlow 优化
- Room 数据库查询效率 → 数据量小，影响不大
- Configuration Change 导致 Timer 重置 → ViewModel 保存状态

---

## 11. 🚀 Enhancement Features (已完成/已取消)

### Overview

在 Week 5-6 初期，为提升用户体验，计划并实现了部分增强功能。根据 MVP 开发优先级，部分功能已完成，部分功能已取消。

**📋 Feature Summary Table**

| # | Feature | Difficulty | Priority | Status | 完成日期 |
|---|---------|-----------|----------|--------|---------|
| 1 | Dark/Light Theme Toggle | ⭐ | P1 | ✅ **Completed** | 2025-11-06 |
| 2 | Sound Effects System | ⭐⭐ | P1 | ✅ **Completed** | 2025-11-07 |
| 3 | Background Music Player | ⭐⭐⭐ | P2 | ❌ **Cancelled** | - |
| 4 | Multi-Language Support | ⭐⭐⭐ | P2 | ❌ **Cancelled** | - |
| 5 | Time Picker Wheel + Stopwatch | ⭐⭐⭐⭐ | P3 | ❌ **Cancelled** | - |

### ✅ 已完成功能

#### 11.1 Dark/Light Theme Toggle ✅

**Status:** ✅ Completed (2025-11-06)

**Purpose:** Provide dark and light themes for different lighting environments.

**Implementation:**
- ✅ Theme toggle button in Dashboard TopAppBar (🌙 icon)
- ✅ Dark color scheme: Deep green (#1B5E20) + Dark gray background (#121212)
- ✅ User preference saved with DataStore
- ✅ Seamless theme switching without app restart

**UI Location:** Dashboard TopAppBar, top-right corner

**Technical Implementation:**
```kotlin
// ThemePreferences.kt - DataStore persistence
class ThemePreferences(context: Context) {
    val isDarkTheme: Flow<Boolean>
    suspend fun toggleTheme()
}

// Theme.kt - Dark color scheme
val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryGreen,
    background = DarkBackground,
    // ...
)
```

**Files Modified:**
- `ThemePreferences.kt` (new)
- `Theme.kt`, `Color.kt`
- `MainActivity.kt`, `DashboardScreen.kt`

---

#### 11.2 Sound Effects System ✅

**Status:** ✅ Completed (2025-11-07)

**Purpose:** Provide immediate audio feedback for key user actions.

**Sound Design:**
- ✅ Nature-themed sounds (game start, piano pause, level win, negative beep)
- ✅ 4 sound effects: Start, Pause, Complete, Cancel
- ✅ Source: freesound.org (CC0 licensed)
- ✅ Total size: ~270 KB

**Implementation:**
- ✅ Use SoundPool API for short audio clips
- ✅ Mute toggle in Dashboard TopAppBar (🔊/🔇 icon)
- ✅ Non-blocking playback
- ✅ Integrated into TimerScreen

**Technical Implementation:**
```kotlin
// SoundManager.kt
class SoundManager(context: Context) {
    private var soundPool: SoundPool
    fun playStart() / playPause() / playComplete() / playCancel()
    fun setMuted(muted: Boolean)
}
```

**Audio Files:**
- `game_start_317318.mp3` (1s)
- `pause_piano_sound_40579.mp3` (1s)
- `level_win_6416.mp3` (3s)
- `ui_beep_menu_negative_02_228338.mp3` (1s)

**Files Modified:**
- `SoundManager.kt` (new)
- `SoundPreferences.kt` (new)
- `MainActivity.kt`, `TimerScreen.kt`, `DashboardScreen.kt`

---

### ❌ 已取消功能（根据 MVP 优先级调整）

#### 11.3 Background Music Player ❌

**Status:** ❌ Cancelled

**取消原因:**
1. **复杂度过高**: 需要 Foreground Service + MediaPlayer + Notification
2. **不属于 MVP 核心功能**: Week 5-6 专注于 Timer + Journal
3. **用户需求优先级低**: 音效系统已满足基本反馈需求

**后续计划:** 可能在 Week 10 或 Post-Project 阶段考虑

---

#### 11.4 Multi-Language Support ❌

**Status:** ❌ Cancelled

**取消原因:**
1. **翻译工作量大**: 需要维护两套完整的 strings.xml
2. **不属于 MVP 核心功能**: 英文 UI 已满足基本需求
3. **测试成本高**: 需要验证所有界面的多语言适配

**已完成工作:**
- 多语言架构设计完成
- `values-zh/strings.xml` 已创建（已删除）
- `LanguagePreferences.kt` 已实现（已删除）

**后续计划:** 可能在 Week 10 或 Post-Project 阶段实现

---

#### 11.5 Time Picker Wheel + Stopwatch Mode ❌

**Status:** ❌ Cancelled

**取消原因:**
1. **实现复杂度极高**: 自定义滚动选择器 + 双计时模式
2. **不符合 Pomodoro 理念**: 固定 25 分钟是核心特性
3. **用户明确要求取消**: 用户在 2025-11-07 明确表示不开发此功能

**设计文档保留:** 供未来参考

**后续计划:** 不考虑实现

### 11.6 Technical Requirements (已完成功能)

**New Dependencies Added:**
```kotlin
// DataStore for theme and sound preferences
implementation("androidx.datastore:datastore-preferences:1.0.0")
```

**Permissions Required:**
- ❌ No special permissions needed (已取消 Foreground Service)

**APK Size Impact:**
- Audio files (sound effects): ~270 KB
- Code: ~50 KB
- Total: ~320 KB increase

### 11.7 Implementation Timeline (实际完成)

**✅ 2025-11-06: Theme Toggle**
- 实际用时: 2.5 hours
- 文件: `ThemePreferences.kt`, `Theme.kt`, `Color.kt`

**✅ 2025-11-07: Sound Effects System**
- 实际用时: 3 hours
- 文件: `SoundManager.kt`, `SoundPreferences.kt`

**Total Time Spent:** 5.5 hours

### 11.8 Testing Checklist (已完成功能)

**Theme Toggle:**
- [x] Theme switching works smoothly
- [x] Preferences persist after app restart
- [x] All screens adapt to dark/light theme
- [x] No UI flicker or lag

**Sound Effects:**
- [x] Sound effects play correctly on timer actions
- [x] Mute toggle functional
- [x] Preferences persist
- [x] No audio conflicts or crashes

**Integration:**
- [x] Theme and sound features work together without conflicts
- [x] DataStore handles multiple preference files correctly

**Detailed Design Document:** See [Feature_Enhancement_Plan.md](./Feature_Enhancement_Plan.md) (已存档)

---

## 12. 🧾 Future Improvements (Post-Project)

### Phase 1: Core Feature Enhancements
- Background Music Player (已设计，待实现)
- Multi-Language Support (已设计，待实现)
- Time Picker Wheel + Stopwatch Mode (已设计，待实现)

### Phase 2: Cloud & Collaboration
- Cloud sync (Firebase) for Heist groups
- Multi-device streak synchronization
- Real-time group challenge updates

### Phase 3: AI & Analytics
- Deeper AI analytics using OpenAI or Gemini APIs
- Personalized study pattern recognition
- Smart break time recommendations

### Phase 4: Data & Integration
- Export focus data to calendar
- CSV/PDF report generation
- Integration with study planner apps

### Phase 5: Customization
- More music types (Lo-fi, classical, ambient)
- Custom sound effects upload
- Customizable Pomodoro durations
- Theme color customization

---

## ✅ Summary

### Document Status
This Technical Development Document (TD) reflects the **current state** of FocusGarden as of **2025-11-13**:

**✅ Completed Phases:**
- Week 1-2: Project setup + theme design
- Week 3-4: UI wireframes + MVVM architecture + Room database
- **Week 5-6:** ✅ **MVP Development COMPLETED**
  - Phase A: Timer 倒计时核心逻辑 (✅ 完成 + Bug 修复)
  - Phase B: Session 数据保存 (✅ 完成)
  - Phase C: Journal 反思对话框 (✅ 完成)
  - Phase D: Journal 数据保存 (✅ 完成)
  - Phase E: Dashboard 数据读取 (✅ 完成)
  - Phase F: Timer 时长调节 (✅ 完成)
- **Week 7-8:** ✅ **Feature Integration COMPLETED**
  - Dashboard 数据可视化 (✅ 完成 - Phase E)
  - Heist Group 模拟数据展示 (✅ 完成)

**🐛 Bug Fixes (2025-11-13):**
- ✅ Fixed HeistViewModel MemberProgress constructor mismatch (12 errors)
- ✅ Fixed MainActivity import conflicts for SoundPreferences/ThemePreferences (4 errors)
- ✅ Updated all code comments to English (Code Standards compliance)
- ✅ Fixed bottom navigation bar transitions (smooth page switching)

**✨ Week 9 Enhancements (2025-11-13):**
- ✅ **Gemini API Integration** - AI-powered insights generation
- ✅ **PDF Export Feature** - Generate professional weekly reports with AI insights
- ✅ Added GeminiService for API communication
- ✅ Added PDFGenerator utility (Android native PDF)
- ✅ Enhanced AISummaryViewModel with PDF generation
- ✅ Updated AI Summary UI with export section

**✨ Timer Skip Enhancement (2025-11-20):**
- ✅ **Skip Timer Function** - Allow users to skip countdown but still count planned duration
- ✅ Added `skipTimer()` method in TimerViewModel
- ✅ Updated `saveReflection()` to use planned duration when skipped
- ✅ Added Skip button in TimerScreen UI (visible during timer)
- ✅ Skip still allows Journal entry and category selection
- ✅ Statistics count planned time, not actual elapsed time

**🐛 Bug Fixes & Debugging (2025-11-20):**
- ✅ Fixed Flow type mismatch (`.collect()` → `.first()`)
- ✅ Fixed AI Summary loading freeze issue
- ✅ Fixed function return type mismatch (Unit → String?)
- ✅ Added comprehensive debug logging throughout
- ⚠️ **Gemini API Issue:** API Key lacks Gemini 1.5 access (404 NOT_FOUND)
  - **Solution:** Disabled Gemini API integration for now
  - **Fallback:** Using local AI algorithm for recommendations
  - **Impact:** PDF still generates successfully with local insights

**📝 Documentation:**
- ✅ Created `BUGS_AND_CHALLENGES.md` for development reflection
- ✅ Documented all bugs encountered and solutions
- ✅ Recorded technical challenges and learnings

**⏳ Next Phase:**
- Resolve Gemini API integration issue
- Week 10: Testing & polish

### Key Features
1. **MVVM Architecture** with Jetpack Compose + Material 3 ✅
2. **Room Database** for local data persistence ✅
3. **Dark/Light Theme** with DataStore preferences ✅
4. **Sound Effects System** for user feedback ✅
5. **Timer + Journal workflow** ✅ **完成**
6. **Dashboard analytics** ✅ **完成** (真实数据显示)
7. **Heist Group challenge** ✅ **完成** (模拟数据展示)
8. **AI Summary generation** (计划中 - Week 9)

### Technical Decisions (Week 5-6)
- ✅ Timer 前台计时 (Kotlin Flow + delay)
- ❌ 不使用 Foreground Service (降低复杂度)
- ✅ Category 在 Journal 时选择
- ✅ MVP 优先: Timer → Journal → Dashboard

### Reference Documents
- [Week 5-6 MVP Development Plan](./Week5-6_MVP_Development_Plan.md) - 详细开发任务
- [Feature Enhancement Plan](./Feature_Enhancement_Plan.md) - 增强功能设计（已存档）

**Ready for CP3406 Part B–C submission** with clear development roadmap and technical documentation.
