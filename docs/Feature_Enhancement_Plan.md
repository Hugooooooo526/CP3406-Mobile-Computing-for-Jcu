# 🚀 FocusGarden - Feature Enhancement Plan

**文档创建日期:** 2025-11-06  
**开发阶段:** Week 5-6 Enhancement Features  
**状态:** 📋 设计完成，待实现

---

## 📋 概述

本文档详细规划了 FocusGarden 应用的 5 个核心功能增强，旨在提升用户体验和应用的专业性。所有功能均经过详细讨论和确认，按照从简单到复杂的顺序实现。

---

## 🎯 功能清单

| # | 功能名称 | 难度 | 预计工时 | 优先级 | 状态 |
|---|---------|------|---------|--------|------|
| 1 | 深色/浅色主题切换 | ⭐ | 2-3h | P1 | ⏳ 待实现 |
| 2 | 音效反馈系统 | ⭐⭐ | 3-4h | P1 | ⏳ 待实现 |
| 3 | 背景音乐播放器 | ⭐⭐⭐ | 5-6h | P2 | ⏳ 待实现 |
| 4 | 多语言支持 (EN/中文) | ⭐⭐⭐ | 6-8h | P2 | ⏳ 待实现 |
| 5 | 滚动时间选择器 + 正计时 | ⭐⭐⭐⭐ | 8-10h | P3 | ⏳ 待实现 |

**总预计工时:** 24-31 小时

---

## 📐 功能详细设计

### 🌓 功能 #1：深色/浅色主题切换

#### 功能描述
提供深色和浅色两种主题，用户可以通过 TopAppBar 的按钮快速切换，提升不同光线环境下的使用体验。

#### 技术实现
- **UI 位置:** Dashboard TopAppBar 右上角
- **图标:** ☀️ (浅色模式) / 🌙 (深色模式)
- **持久化:** 使用 `DataStore Preferences` 保存用户选择
- **状态管理:** 在 `Theme.kt` 中使用 `mutableStateOf` 管理当前主题

#### 配色方案

**浅色主题 (Light Mode) - 现有方案**
```kotlin
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E7D32),        // 主绿色
    primaryContainer = Color(0xFFE8F5E9), // 浅绿色
    secondary = Color(0xFF43A047),      // 次级绿色
    background = Color(0xFFFFFFFF),     // 白色背景
    surface = Color(0xFFFFFFFF),        // 白色表面
    onPrimary = Color(0xFFFFFFFF),      // 主色上的文字
    onBackground = Color(0xFF1C1B1F),   // 背景上的文字
)
```

**深色主题 (Dark Mode) - 新增方案**
```kotlin
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),        // 明亮绿色（深色模式下的主色）
    primaryContainer = Color(0xFF1B5E20), // 深绿色
    secondary = Color(0xFF66BB6A),      // 次级明亮绿色
    background = Color(0xFF121212),     // 深灰背景
    surface = Color(0xFF1E1E1E),        // 深灰表面
    onPrimary = Color(0xFF003300),      // 主色上的深色文字
    onBackground = Color(0xFFE0E0E0),   // 背景上的浅色文字
    onSurface = Color(0xFFE0E0E0),      // 表面上的浅色文字
)
```

#### UI 交互流程
1. 用户点击 TopAppBar 的主题切换按钮
2. 应用切换到对应主题，图标变化
3. 主题偏好保存到 DataStore
4. 下次启动应用时自动应用已保存的主题

#### 文件变更
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/ui/theme/Theme.kt`
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/ui/screens/DashboardScreen.kt`
- ➕ 新建 `app/src/main/java/com/jcu/focusgarden/data/preferences/ThemePreferences.kt`

---

### 🔊 功能 #2：音效反馈系统

#### 功能描述
在用户进行关键操作时播放短音效，提供即时反馈，提升交互体验。采用自然系音效符合 "Garden" 主题。

#### 触发场景
| 场景 | 音效描述 | 时长 | 文件名 |
|------|---------|------|--------|
| 开始专注 | 柔和的水滴声 | ~0.5s | `sound_focus_start.mp3` |
| 暂停专注 | 轻柔的竹子敲击声 | ~0.3s | `sound_focus_pause.mp3` |
| 完成专注 | 鸟鸣 + 风铃声 | ~1.5s | `sound_focus_complete.mp3` |
| 取消专注 | 轻微的叶子沙沙声 | ~0.4s | `sound_focus_cancel.mp3` |

#### 技术实现
- **API:** 使用 `SoundPool` (适合短音效)
- **音效来源:** [freesound.org](https://freesound.org) (CC0 或 CC BY 授权)
- **音效格式:** MP3 或 OGG
- **音效大小:** 每个 < 100KB

#### 静音控制
- **UI 位置:** Dashboard TopAppBar 右侧（主题切换按钮旁边）
- **图标:** 🔊 (开启) / 🔇 (静音)
- **持久化:** 使用 `DataStore Preferences` 保存用户选择

#### 代码结构
```kotlin
// 新建 SoundManager.kt
class SoundManager(private val context: Context) {
    private val soundPool: SoundPool
    private var soundStartId: Int = 0
    private var soundPauseId: Int = 0
    private var soundCompleteId: Int = 0
    private var soundCancelId: Int = 0
    private var isMuted: Boolean = false
    
    fun playStart() { if (!isMuted) soundPool.play(soundStartId, ...) }
    fun playPause() { ... }
    fun playComplete() { ... }
    fun playCancel() { ... }
    fun toggleMute() { isMuted = !isMuted }
}
```

#### 文件变更
- ➕ 新建 `app/src/main/java/com/jcu/focusgarden/utils/SoundManager.kt`
- ➕ 新建 `app/src/main/res/raw/sound_focus_start.mp3`
- ➕ 新建 `app/src/main/res/raw/sound_focus_pause.mp3`
- ➕ 新建 `app/src/main/res/raw/sound_focus_complete.mp3`
- ➕ 新建 `app/src/main/res/raw/sound_focus_cancel.mp3`
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/ui/screens/TimerScreen.kt`
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/ui/screens/DashboardScreen.kt`

---

### 🎵 功能 #3：背景音乐播放器

#### 功能描述
提供全局背景音乐播放功能，用户点击一个按钮即可在所有页面随机播放白噪音，帮助用户保持专注。**设计原则：简单、全局、非侵入式**。

#### 用户交互（极简设计）
- **唯一入口:** Dashboard 快速操作区域新增 "🎵 Focus Music" 按钮
- **播放逻辑:**
  1. 点击按钮 → 随机选择一首音乐 → 开始循环播放
  2. 再次点击 → 停止播放
- **状态显示:** 按钮颜色变化（播放中为深绿色，停止时为浅色）
- **跨页面:** 在应用内所有页面持续播放，不受页面切换影响

#### 音乐资源
| 音乐类型 | 描述 | 文件名 | 时长 | 大小 |
|---------|------|--------|------|------|
| 雨声 | 中等雨滴在树叶上 | `ambient_rain.mp3` | 5 min | ~2MB |
| 海浪声 | 柔和的海浪拍打声 | `ambient_ocean.mp3` | 5 min | ~2MB |
| 森林声 | 鸟鸣 + 风吹树叶 | `ambient_forest.mp3` | 5 min | ~2MB |
| 溪流声 | 小溪流水声 | `ambient_stream.mp3` | 5 min | ~2MB |

**总大小:** ~8MB（嵌入 APK）

#### 技术实现
- **API:** 使用 `MediaPlayer` (适合长音频)
- **播放模式:** 循环播放 (`setLooping(true)`)
- **音量控制:** 使用系统音量（媒体音量）
- **生命周期:** 使用 Foreground Service 确保后台播放
- **随机逻辑:** 每次点击"开始"时随机选择一首

#### 代码结构
```kotlin
// 新建 MusicPlayerService.kt (Foreground Service)
class MusicPlayerService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val musicList = listOf(
        R.raw.ambient_rain,
        R.raw.ambient_ocean,
        R.raw.ambient_forest,
        R.raw.ambient_stream
    )
    
    fun playRandom() {
        val randomMusic = musicList.random()
        mediaPlayer?.apply {
            reset()
            setDataSource(context, Uri.parse("android.resource://$packageName/$randomMusic"))
            setLooping(true)
            prepare()
            start()
        }
    }
    
    fun stop() { mediaPlayer?.stop() }
}
```

#### UI 设计
- **位置:** DashboardScreen 快速操作区域（第 4 个按钮）
- **按钮文案:**
  - 停止状态: "🎵 Focus Music"
  - 播放状态: "⏸️ Stop Music"
- **视觉反馈:** 播放时按钮背景色为深绿色 (#1B5E20)

#### 文件变更
- ➕ 新建 `app/src/main/java/com/jcu/focusgarden/service/MusicPlayerService.kt`
- ➕ 新建 `app/src/main/res/raw/ambient_rain.mp3`
- ➕ 新建 `app/src/main/res/raw/ambient_ocean.mp3`
- ➕ 新建 `app/src/main/res/raw/ambient_forest.mp3`
- ➕ 新建 `app/src/main/res/raw/ambient_stream.mp3`
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/ui/screens/DashboardScreen.kt`
- ✏️ 修改 `app/src/main/AndroidManifest.xml` (添加 Service 和权限)

---

### 🌍 功能 #4：多语言支持 (English + 简体中文)

#### 功能描述
支持英语和简体中文两种界面语言，用户可在应用内切换。AI Summary 统一使用英语，简化实现。

#### 支持语言
- 🇺🇸 **English** (默认，`values/strings.xml`)
- 🇨🇳 **简体中文** (`values-zh/strings.xml`)

#### 语言切换器设计
- **位置:** Dashboard TopAppBar 右侧（主题和音效按钮旁）
- **UI 组件:** IconButton with DropdownMenu
- **图标:** 🌐
- **下拉选项:**
  - English
  - 简体中文

#### 技术实现
```kotlin
// 使用 AppCompatDelegate 动态切换语言
AppCompatDelegate.setApplicationLocales(
    LocaleListCompat.forLanguageTags("zh-CN") // 或 "en-US"
)
```

#### 翻译范围
| 模块 | 翻译内容 | 备注 |
|------|---------|------|
| Dashboard | 所有按钮、标题、卡片文字 | ✅ 全部翻译 |
| Timer | 所有按钮、对话框文字 | ✅ 全部翻译 |
| Heist | 所有界面文字 | ✅ 全部翻译 |
| AI Summary | **仅标题和按钮** | ⚠️ 内容统一用英语 |
| Navigation | 底部导航栏文字 | ✅ 全部翻译 |

#### 字符串资源示例
```xml
<!-- values/strings.xml (English) -->
<string name="dashboard_title">FocusGarden</string>
<string name="today_focus">Today\'s Focus</string>
<string name="start_focus">Start Focus</string>

<!-- values-zh/strings.xml (简体中文) -->
<string name="dashboard_title">专注花园</string>
<string name="today_focus">今日专注</string>
<string name="start_focus">开始专注</string>
```

#### AI Summary 语言策略
- **标题和按钮:** 翻译（"AI 总结" / "AI Summary"）
- **数据内容:** 统一使用英语（如 "540 min", "Weekly Summary"）
- **推荐文本:** 统一使用英语（简化实现）

#### 文件变更
- ✏️ 扩展 `app/src/main/res/values/strings.xml` (现有 59 行 → ~100 行)
- ➕ 新建 `app/src/main/res/values-zh/strings.xml` (~100 行)
- ✏️ 修改所有 Screen 文件，将硬编码文字改为 `stringResource(R.string.xxx)`
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/ui/screens/DashboardScreen.kt` (添加语言切换器)
- ➕ 新建 `app/src/main/java/com/jcu/focusgarden/utils/LanguageManager.kt`

---

### ⏱️ 功能 #5：滚动时间选择器 + 正计时模式

#### 功能描述
提供灵活的计时器配置功能：
1. **滚动时间选择器:** 类似 iOS 风格的滚动选择器，自定义倒计时时长（5-120 分钟）
2. **正计时模式:** 添加从 0 开始向上计时的选项，适合不确定时长的学习场景

#### 🎡 滚动时间选择器

##### UI 位置
- TimerScreen 中央显示区域上方
- 添加一个 "⚙️ Custom" 按钮，点击后弹出 BottomSheet

##### 选择器设计
- **UI 风格:** 3D 滚动效果（中间项高亮，上下项半透明）
- **时间范围:** 5 分钟 ~ 120 分钟
- **步进单位:** 5 分钟（5, 10, 15, 20, 25, ..., 120）
- **预设快捷选项:** 15 min / 25 min (Pomodoro) / 45 min / 60 min

##### 技术实现
```kotlin
// 使用 Jetpack Compose LazyColumn 模拟滚动选择器
@Composable
fun TimePickerWheel(
    minutes: List<Int>,
    selectedMinute: Int,
    onMinuteSelected: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        state = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)
    ) {
        itemsIndexed(minutes) { index, minute ->
            val isSelected = minute == selectedMinute
            Text(
                text = "$minute min",
                fontSize = if (isSelected) 24.sp else 18.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Primary else Gray,
                modifier = Modifier
                    .clickable { onMinuteSelected(minute) }
                    .alpha(if (isSelected) 1f else 0.5f)
            )
        }
    }
}
```

##### 可选时间列表
```kotlin
val minuteOptions = listOf(
    5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60,
    65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120
)
```

#### ⏫ 正计时模式 (Stopwatch Mode)

##### UI 设计
- **切换位置:** TimerScreen 顶部添加 SegmentedButton (Material 3)
  - Option 1: "⏱️ Timer" (倒计时)
  - Option 2: "⏫ Stopwatch" (正计时)

##### 正计时功能
- **起始值:** 00:00
- **计时方向:** 向上增加（00:00 → 00:01 → ... → 无限）
- **控制按钮:** 
  - ▶️ Start / ⏸️ Pause
  - 🔄 Reset（回到 00:00）
- **完成方式:** 用户手动点击 "⏹️ Finish" 按钮
- **反思对话框:** 点击 Finish 后弹出（与倒计时相同）

##### 数据保存
- 正计时模式结束时，保存实际用时到 `SessionEntity`
- `duration` 字段存储用户主动停止时的秒数

#### 代码结构
```kotlin
// TimerViewModel.kt 扩展
enum class TimerMode {
    COUNTDOWN,  // 倒计时模式
    STOPWATCH   // 正计时模式
}

data class TimerState(
    val mode: TimerMode = TimerMode.COUNTDOWN,
    val customDuration: Int = 25, // 分钟
    val remainingSeconds: Int = 25 * 60,
    val elapsedSeconds: Int = 0, // 正计时使用
    val isRunning: Boolean = false
)
```

#### 用户流程

**倒计时模式流程:**
1. 用户点击 "⚙️ Custom" 按钮
2. BottomSheet 弹出，显示滚动选择器
3. 用户滚动选择时间（如 45 分钟）
4. 点击 "Confirm" → 计时器设置为 45:00
5. 点击 ▶️ 开始倒计时
6. 时间到达 00:00 → 自动弹出反思对话框

**正计时模式流程:**
1. 用户切换到 "⏫ Stopwatch" 模式
2. 计时器显示 00:00
3. 点击 ▶️ 开始正计时（00:00 → 00:01 → ...）
4. 用户专注完成后点击 "⏹️ Finish" 按钮
5. 手动触发反思对话框

#### 文件变更
- ✏️ 大幅修改 `app/src/main/java/com/jcu/focusgarden/ui/screens/TimerScreen.kt`
- ✏️ 修改 `app/src/main/java/com/jcu/focusgarden/viewmodel/TimerViewModel.kt`
- ➕ 新建 `app/src/main/java/com/jcu/focusgarden/ui/components/TimePickerWheel.kt`

---

## 📊 实现优先级和时间规划

### 实现顺序（建议）

**第一阶段: 视觉和反馈增强 (Week 5 前半)**
1. ✅ 功能 #1: 深色/浅色主题切换 (2-3h)
2. ✅ 功能 #2: 音效反馈系统 (3-4h)

**第二阶段: 多媒体和国际化 (Week 5 后半)**
3. ✅ 功能 #3: 背景音乐播放器 (5-6h)
4. ✅ 功能 #4: 多语言支持 (6-8h)

**第三阶段: 高级交互功能 (Week 6)**
5. ✅ 功能 #5: 滚动时间选择器 + 正计时模式 (8-10h)

**总计:** 24-31 小时（约 3-4 天全职开发）

---

## 🧪 测试计划

### 功能测试清单

**功能 #1: 主题切换**
- [ ] 点击主题按钮可以正常切换
- [ ] 深色模式下所有文字可读
- [ ] 主题偏好在重启后保持
- [ ] 所有界面适配深色模式

**功能 #2: 音效**
- [ ] 开始专注播放正确音效
- [ ] 暂停专注播放正确音效
- [ ] 完成专注播放正确音效
- [ ] 静音按钮可以关闭所有音效
- [ ] 音效不与系统音乐冲突

**功能 #3: 背景音乐**
- [ ] 点击按钮可以开始播放随机音乐
- [ ] 音乐在切换页面时继续播放
- [ ] 再次点击可以停止音乐
- [ ] 音乐循环播放无缝衔接
- [ ] 应用退出时音乐停止

**功能 #4: 多语言**
- [ ] 语言切换器显示正确选项
- [ ] 切换到中文后所有界面翻译正确
- [ ] 切换回英文正常
- [ ] 语言偏好在重启后保持
- [ ] AI Summary 内容保持英语

**功能 #5: 时间选择器 + 正计时**
- [ ] 滚动选择器可以选择 5-120 分钟
- [ ] 选择后计时器显示正确时间
- [ ] 正计时模式从 00:00 开始
- [ ] 正计时可以正常暂停和重置
- [ ] Finish 按钮触发反思对话框
- [ ] 两种模式的数据都能正确保存

---

## 📁 新增/修改文件清单

### 新增文件 (15 个)

**数据/工具类 (4 个)**
- `app/src/main/java/com/jcu/focusgarden/data/preferences/ThemePreferences.kt`
- `app/src/main/java/com/jcu/focusgarden/utils/SoundManager.kt`
- `app/src/main/java/com/jcu/focusgarden/utils/LanguageManager.kt`
- `app/src/main/java/com/jcu/focusgarden/service/MusicPlayerService.kt`

**UI 组件 (1 个)**
- `app/src/main/java/com/jcu/focusgarden/ui/components/TimePickerWheel.kt`

**资源文件 (10 个)**
- `app/src/main/res/raw/sound_focus_start.mp3` (~50KB)
- `app/src/main/res/raw/sound_focus_pause.mp3` (~30KB)
- `app/src/main/res/raw/sound_focus_complete.mp3` (~150KB)
- `app/src/main/res/raw/sound_focus_cancel.mp3` (~40KB)
- `app/src/main/res/raw/ambient_rain.mp3` (~2MB)
- `app/src/main/res/raw/ambient_ocean.mp3` (~2MB)
- `app/src/main/res/raw/ambient_forest.mp3` (~2MB)
- `app/src/main/res/raw/ambient_stream.mp3` (~2MB)
- `app/src/main/res/values-zh/strings.xml` (~100 行)
- `app/src/main/res/xml/locales_config.xml`

### 修改文件 (8 个)

**主题和颜色**
- `app/src/main/java/com/jcu/focusgarden/ui/theme/Theme.kt`
- `app/src/main/java/com/jcu/focusgarden/ui/theme/Color.kt`

**界面**
- `app/src/main/java/com/jcu/focusgarden/ui/screens/DashboardScreen.kt`
- `app/src/main/java/com/jcu/focusgarden/ui/screens/TimerScreen.kt`

**ViewModel**
- `app/src/main/java/com/jcu/focusgarden/viewmodel/TimerViewModel.kt`

**资源和配置**
- `app/src/main/res/values/strings.xml`
- `app/src/main/AndroidManifest.xml`
- `app/build.gradle.kts`

---

## 🎨 UI/UX 改进总结

### 新增 UI 元素

**Dashboard TopAppBar 右侧 (4 个图标按钮)**
```
+------------------------------------------+
| FocusGarden 🌱    |  🌐  🔊  ☀️  |
+------------------------------------------+
```
- 🌐 语言切换器 (DropdownMenu)
- 🔊 音效开关 (IconButton)
- ☀️/🌙 主题切换 (IconButton)

**Dashboard 快速操作区 (新增第 4 个按钮)**
```
[ 🎯 Start Focus ]  [ 📓 View Journal ]
[ 🤖 AI Summary  ]  [ 🎵 Focus Music  ]
```

**TimerScreen 新增元素**
```
+------------------------------------------+
|  [ ⏱️ Timer | ⏫ Stopwatch ]  ⚙️ Custom   |
|                                          |
|           ◯ 25:00 ◯                     |
|                                          |
+------------------------------------------+
```

### 用户体验提升

| 功能 | 提升点 | 影响 |
|------|--------|------|
| 主题切换 | 适应不同光线环境 | ⭐⭐⭐⭐⭐ |
| 音效反馈 | 即时操作反馈 | ⭐⭐⭐⭐ |
| 背景音乐 | 提升专注氛围 | ⭐⭐⭐⭐⭐ |
| 多语言 | 扩大用户群体 | ⭐⭐⭐⭐ |
| 时间选择器 | 灵活性大幅提升 | ⭐⭐⭐⭐⭐ |
| 正计时模式 | 适应更多场景 | ⭐⭐⭐⭐ |

---

## 🔒 技术要求和依赖

### 新增 Gradle 依赖
```kotlin
// DataStore (主题和音效偏好)
implementation("androidx.datastore:datastore-preferences:1.0.0")

// 可能需要的权限处理
implementation("androidx.activity:activity-compose:1.7.2")
```

### AndroidManifest 权限
```xml
<!-- Foreground Service (背景音乐) -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />

<!-- Service 声明 -->
<service
    android:name=".service.MusicPlayerService"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="mediaPlayback" />
```

---

## 📈 项目影响评估

### APK 大小影响
| 资源类型 | 大小 |
|---------|------|
| 音效文件 (4 个) | ~270 KB |
| 背景音乐 (4 个) | ~8 MB |
| 代码和资源 | ~100 KB |
| **总增量** | **~8.4 MB** |

### 性能影响
- **内存:** +5-10 MB (MediaPlayer 运行时)
- **电池:** 背景音乐播放时略微增加（可接受）
- **启动速度:** 无影响（懒加载）

---

## ✅ 验收标准

### 功能完整性
- [ ] 所有 5 个功能完整实现
- [ ] 所有 UI 元素符合设计稿
- [ ] 无编译错误和 Lint 警告

### 用户体验
- [ ] 所有交互流畅，无卡顿
- [ ] 音效和音乐播放无延迟
- [ ] 主题切换无闪烁
- [ ] 语言切换立即生效

### 数据持久化
- [ ] 主题偏好保存成功
- [ ] 音效开关状态保存成功
- [ ] 语言选择保存成功
- [ ] 自定义时长保存成功

---

## 🚀 后续优化方向（可选）

### 高级功能（Week 7-8 考虑）
1. **更多背景音乐:** 添加 Lo-fi、轻音乐类型
2. **音乐音量调节:** 独立于系统音量的音乐音量滑块
3. **更多语言:** 马来语、繁体中文
4. **主题自动切换:** 根据时间自动切换深浅色主题
5. **自定义音效:** 允许用户上传自己的音效

---

## 📞 联系信息

**开发者:** Cui Langxuan (Hugo)  
**学号:** 14706438  
**邮箱:** langxuan.cui@my.jcu.edu.au

---

## 📝 变更日志

| 日期 | 版本 | 变更内容 |
|------|------|---------|
| 2025-11-06 | v1.0 | 初始文档创建，5 个功能详细设计完成 |

---

**文档状态:** ✅ 设计完成  
**下一步:** 开始实现功能 #1 (主题切换)

