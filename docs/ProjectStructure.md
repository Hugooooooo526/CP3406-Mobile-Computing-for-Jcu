# 📁 FocusGarden 项目结构

**完整的文件组织架构**  
**最后更新:** 2025-11-03

---

## 🌳 完整目录树

```
CP3406-Mobile-Computing-for-Jcu/
│
├── 📄 README.md                          # 项目概述和说明
├── 📄 DEVELOPMENT_SUMMARY.md             # 开发完成总结
├── 📄 build.gradle.kts                   # 项目级 Gradle 配置
├── 📄 settings.gradle.kts                # Gradle 设置
├── 📄 gradle.properties                  # Gradle 属性
│
├── 📁 docs/                              # 文档目录
│   ├── 📄 TechnicalDevelopmentDocument.md    # 技术开发文档（TD）
│   ├── 📄 Week3-4_Progress_Report.md         # Week 3-4 进度报告
│   ├── 📄 QuickStartGuide.md                 # 快速开始指南
│   ├── 📄 GitCommitGuide.md                  # Git 提交指南
│   ├── 📄 ProjectStructure.md                # 项目结构文档（本文档）
│   ├── 📄 CP3405_android_focusgarden_UI.pdf  # UI 设计文档
│   └── 📄 FocusGarden_Planning_Document.pdf  # 规划文档
│
├── 📁 gradle/                            # Gradle Wrapper
│   └── wrapper/
│       └── gradle-wrapper.properties
│
└── 📁 app/                               # 应用主模块
    │
    ├── 📄 build.gradle.kts              # 应用级 Gradle 配置
    ├── 📄 proguard-rules.pro            # ProGuard 混淆规则
    │
    └── 📁 src/
        └── 📁 main/
            │
            ├── 📄 AndroidManifest.xml   # Android 清单文件
            │
            ├── 📁 java/com/jcu/focusgarden/   # 主代码目录
            │   │
            │   ├── 📄 MainActivity.kt         # 应用入口
            │   │
            │   ├── 📁 ui/                     # UI 层
            │   │   │
            │   │   ├── 📁 screens/            # 界面屏幕
            │   │   │   ├── 📄 DashboardScreen.kt     # Dashboard 界面
            │   │   │   ├── 📄 TimerScreen.kt         # Timer 界面
            │   │   │   ├── 📄 HeistScreen.kt         # Heist Group 界面
            │   │   │   └── 📄 AISummaryScreen.kt     # AI Summary 界面
            │   │   │
            │   │   ├── 📁 components/         # 可复用组件
            │   │   │   ├── 📄 FocusCard.kt           # 卡片组件
            │   │   │   ├── 📄 DonutChart.kt          # 环形图
            │   │   │   └── 📄 WeeklyBarChart.kt      # 周进度柱状图
            │   │   │
            │   │   ├── 📁 navigation/         # 导航系统
            │   │   │   ├── 📄 Navigation.kt          # 路由配置
            │   │   │   └── 📄 FocusGardenApp.kt      # 主应用组件（含底部导航）
            │   │   │
            │   │   └── 📁 theme/              # Material 3 主题
            │   │       ├── 📄 Color.kt               # 颜色定义
            │   │       ├── 📄 Theme.kt               # 主题配置
            │   │       └── 📄 Type.kt                # 字体排版
            │   │
            │   ├── 📁 viewmodel/              # ViewModel 层
            │   │   ├── 📄 DashboardViewModel.kt      # Dashboard 状态管理
            │   │   ├── 📄 TimerViewModel.kt          # Timer 状态管理
            │   │   ├── 📄 HeistViewModel.kt          # Heist Group 状态管理
            │   │   └── 📄 AISummaryViewModel.kt      # AI Summary 状态管理
            │   │
            │   ├── 📁 data/                   # 数据层
            │   │   │
            │   │   ├── 📁 local/              # 本地数据存储
            │   │   │   │
            │   │   │   ├── 📁 entity/         # Room 实体
            │   │   │   │   ├── 📄 SessionEntity.kt   # 专注会话实体
            │   │   │   │   ├── 📄 JournalEntity.kt   # 反思记录实体
            │   │   │   │   └── 📄 GroupEntity.kt     # 小组实体
            │   │   │   │
            │   │   │   ├── 📁 dao/            # 数据访问对象
            │   │   │   │   ├── 📄 SessionDao.kt      # Session DAO
            │   │   │   │   ├── 📄 JournalDao.kt      # Journal DAO
            │   │   │   │   └── 📄 GroupDao.kt        # Group DAO
            │   │   │   │
            │   │   │   ├── 📁 converter/      # 类型转换器
            │   │   │   │   └── 📄 StringListConverter.kt
            │   │   │   │
            │   │   │   └── 📄 FocusGardenDatabase.kt # Room 数据库配置
            │   │   │
            │   │   └── 📁 repository/         # Repository 层
            │   │       ├── 📄 SessionRepository.kt   # Session 仓库
            │   │       ├── 📄 JournalRepository.kt   # Journal 仓库
            │   │       └── 📄 GroupRepository.kt     # Group 仓库
            │   │
            │   └── 📁 domain/                 # 领域层
            │       │
            │       ├── 📁 model/              # 领域模型
            │       │   └── 📄 Summary.kt             # AI 总结模型
            │       │
            │       └── 📁 usecase/            # 用例
            │           └── 📄 GenerateSummaryUseCase.kt  # AI 总结生成
            │
            └── 📁 res/                        # 资源文件
                │
                ├── 📁 values/
                │   ├── 📄 strings.xml         # 字符串资源
                │   └── 📄 themes.xml          # 主题样式
                │
                ├── 📁 mipmap-*/               # 应用图标（各分辨率）
                │   └── ic_launcher.png
                │
                └── 📁 xml/
                    ├── 📄 backup_rules.xml
                    └── 📄 data_extraction_rules.xml
```

---

## 📊 文件统计

| 类别 | 数量 | 说明 |
|------|------|------|
| **Kotlin 文件** | 30 | 所有 .kt 源代码文件 |
| **UI Screens** | 4 | Dashboard, Timer, Heist, AI Summary |
| **UI Components** | 3 | FocusCard, DonutChart, WeeklyBarChart |
| **ViewModels** | 4 | 对应 4 个主界面 |
| **Room Entities** | 3 | Session, Journal, Group |
| **DAOs** | 3 | 数据访问接口 |
| **Repositories** | 3 | 数据仓库 |
| **Use Cases** | 1 | AI 总结生成 |
| **Navigation** | 2 | 路由配置 + 主应用 |
| **Theme Files** | 3 | Color, Theme, Type |
| **文档文件** | 7 | Markdown + PDF |

---

## 🏗️ 架构分层

### Layer 1: UI Layer（表现层）
```
ui/
├── screens/          → 界面屏幕
├── components/       → 可复用组件
├── navigation/       → 导航系统
└── theme/            → Material 3 主题
```

**职责:**
- 渲染 UI 界面
- 处理用户交互
- 显示数据（从 ViewModel 获取）

---

### Layer 2: ViewModel Layer（视图模型层）
```
viewmodel/
├── DashboardViewModel.kt
├── TimerViewModel.kt
├── HeistViewModel.kt
└── AISummaryViewModel.kt
```

**职责:**
- 管理 UI 状态（StateFlow）
- 处理 UI 逻辑
- 调用 Repository 获取数据
- 生命周期感知

---

### Layer 3: Data Layer（数据层）
```
data/
├── local/
│   ├── entity/       → Room 实体
│   ├── dao/          → 数据访问对象
│   ├── converter/    → 类型转换器
│   └── FocusGardenDatabase.kt
└── repository/       → 数据仓库抽象
```

**职责:**
- 本地数据持久化（Room）
- 数据访问抽象
- 数据源管理

---

### Layer 4: Domain Layer（领域层）
```
domain/
├── model/            → 业务模型
└── usecase/          → 用例（业务逻辑）
```

**职责:**
- 业务规则
- 数据转换
- 复杂计算（AI 总结算法）

---

## 📋 关键文件说明

### 入口文件
| 文件 | 说明 |
|------|------|
| `MainActivity.kt` | 应用入口点，设置 Compose 主题和导航 |
| `FocusGardenApp.kt` | 主应用组件，包含底部导航栏 |
| `Navigation.kt` | 路由配置和导航逻辑 |

### UI 界面（按 TD 文档 4.3 节）
| 文件 | 说明 | 行数 |
|------|------|------|
| `DashboardScreen.kt` | 首页，显示今日专注、周进度、工作负载 | ~260 |
| `TimerScreen.kt` | Pomodoro 计时器，反思对话框 | ~300 |
| `HeistScreen.kt` | 小组挑战，成员进度追踪 | ~280 |
| `AISummaryScreen.kt` | AI 周总结，趋势图，建议列表 | ~320 |

### 可复用组件
| 文件 | 说明 |
|------|------|
| `FocusCard.kt` | 通用卡片组件（16dp 圆角，柔和阴影）|
| `DonutChart.kt` | 环形图（学术 60% / 个人 40%）|
| `WeeklyBarChart.kt` | 7 天柱状图（渐变绿色）|

### 数据模型（按 TD 文档第 5 节）
| 实体 | 表名 | 说明 |
|------|------|------|
| `SessionEntity` | sessions | 专注会话记录 |
| `JournalEntity` | journals | 反思记录（外键关联 Session）|
| `GroupEntity` | groups | Heist Group 小组信息 |

### DAO 接口
| DAO | 方法数 | 说明 |
|-----|--------|------|
| `SessionDao` | 12 | 会话的 CRUD + 统计查询 |
| `JournalDao` | 8 | 反思记录的 CRUD + 心情趋势 |
| `GroupDao` | 6 | 小组的 CRUD + 查询 |

### 业务逻辑
| 文件 | 说明 |
|------|------|
| `GenerateSummaryUseCase.kt` | AI 总结算法（按 TD 文档第 6 节）|
| `Summary.kt` | 总结数据模型 |

---

## 🎨 主题系统

### Color.kt - 色彩定义
```kotlin
PrimaryGreen      = #2E7D32   // 主色调
PrimaryContainer  = #E8F5E9   // 卡片背景
ProgressGreenStart= #43A047   // 渐变起始
ProgressGreenEnd  = #81C784   // 渐变结束
AcademicBlue      = #1976D2   // 学术类别
PersonalOrange    = #FF6F00   // 个人类别
HeistYellow       = #FFF8E1   // Heist Group 卡片
```

### Type.kt - 字体系统
```kotlin
Display  (57sp, 45sp, 36sp)  → 大标题
Headline (32sp, 28sp, 24sp)  → 屏幕标题
Title    (22sp, 16sp, 14sp)  → 卡片标题
Body     (16sp, 14sp, 12sp)  → 正文
Label    (14sp, 12sp, 11sp)  → 按钮和标签
```

---

## 🔗 依赖关系图

```
MainActivity
    ↓
FocusGardenApp (Bottom Nav)
    ↓
Navigation (NavHost)
    ↓
┌─────────┬─────────┬─────────┬─────────┐
│Dashboard│  Timer  │  Heist  │AI Summary│
│Screen   │ Screen  │ Screen  │Screen   │
└────┬────┴────┬────┴────┬────┴────┬────┘
     │         │         │         │
     └────┬────┴────┬────┴────┬────┘
          │         │         │
      ViewModel Layer
          │         │         │
     └────┬────┴────┬────┴────┬────┘
          │         │         │
     Repository Layer
          │         │         │
     └────┬────┴────┬────┴────┬────┘
          │         │         │
       Room Database
          │
      SQLite (Local Storage)
```

---

## 📦 Gradle 依赖

### 核心库
```kotlin
// Jetpack Compose
androidx.compose:compose-bom:2023.10.01
androidx.compose.material3:material3

// Navigation
androidx.navigation:navigation-compose:2.7.5

// Room Database
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3

// Gson
com.google.code.gson:gson:2.10.1
```

---

## 🚀 构建配置

### Gradle 版本
- **Gradle:** 8.2
- **Android Gradle Plugin:** 8.2.0
- **Kotlin:** 1.9.20
- **KSP:** 1.9.20-1.0.14

### SDK 版本
- **Compile SDK:** 34 (Android 14)
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 34 (Android 14)

### 构建类型
- **Debug:** 开发调试版本
- **Release:** 生产发布版本（ProGuard 混淆）

---

## 📝 命名规范

### 文件命名
- **Screen:** `*Screen.kt` (PascalCase)
- **ViewModel:** `*ViewModel.kt`
- **Repository:** `*Repository.kt`
- **Entity:** `*Entity.kt`
- **DAO:** `*Dao.kt`
- **Component:** 描述性名称 (PascalCase)

### 包命名
- 全小写
- 使用句点分隔
- 遵循域名反向规则：`com.jcu.focusgarden`

### 类命名
- PascalCase（大驼峰）
- 清晰描述职责
- 例：`DashboardScreen`, `SessionRepository`

### 函数命名
- camelCase（小驼峰）
- 动词开头
- 例：`loadDashboardData()`, `generateSummary()`

### 变量命名
- camelCase
- 描述性名称
- 例：`todayFocusMinutes`, `currentStreak`

---

## 🎯 代码质量标准

### ✅ 已实现
- [x] Kotlin 编码规范
- [x] 完整的函数注释
- [x] 清晰的文件结构
- [x] 一致的命名规范
- [x] Material 3 设计系统
- [x] MVVM 架构模式
- [x] 零 Lint 错误
- [x] 零编译错误

### 📋 代码审查清单
- [x] 所有 public 函数有注释
- [x] 复杂逻辑有解释
- [x] 魔法数字使用常量
- [x] 资源字符串外部化
- [x] 无硬编码值
- [x] 遵循单一职责原则

---

## 🔍 快速查找指南

### 需要修改 UI？
👉 查看 `app/src/main/java/com/jcu/focusgarden/ui/screens/`

### 需要添加数据库表？
👉 查看 `app/src/main/java/com/jcu/focusgarden/data/local/entity/`

### 需要修改颜色？
👉 查看 `app/src/main/java/com/jcu/focusgarden/ui/theme/Color.kt`

### 需要添加导航路由？
👉 查看 `app/src/main/java/com/jcu/focusgarden/ui/navigation/Navigation.kt`

### 需要修改业务逻辑？
👉 查看 `app/src/main/java/com/jcu/focusgarden/domain/usecase/`

### 需要修改字符串？
👉 查看 `app/src/main/res/values/strings.xml`

---

## 📚 相关文档

- [Technical Development Document](./TechnicalDevelopmentDocument.md) - 完整技术规范
- [Week 3-4 Progress Report](./Week3-4_Progress_Report.md) - 开发进度
- [Quick Start Guide](./QuickStartGuide.md) - 使用指南
- [Git Commit Guide](./GitCommitGuide.md) - Git 提交规范

---

**文档版本:** 1.0  
**最后更新:** 2025-11-03  
**维护者:** Cui Langxuan (Hugo)





