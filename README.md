<!-- User Scenario
Scenario: Mobile Application for Student Focus Management
Sarah is a university student who struggles to maintain consistent study habits and productivity in her academic life. Despite her best efforts to stay organized, she finds it difficult to track her focus time, understand her study patterns, and maintain motivation throughout the semester. Sarah believes she needs a comprehensive solution that not only helps her manage study sessions but also provides insights into her productivity patterns and keeps her accountable. The application should help Sarah establish structured focus sessions, reflect on her learning experiences, visualize her progress over time, and collaborate with peers for mutual motivation, ultimately enabling her to achieve better academic outcomes and develop sustainable study habits. -->

# FocusGarden - Mobile Focus Management Application Presentation

## 0. Application Preview

<!-- Screenshot Placeholders - Please replace with actual application screenshots -->

<div style="display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 20px;">
    <img src="[PLACEHOLDER: Timer Screen Screenshot]" alt="Focus Timer Interface" width="30%" />
    <img src="[PLACEHOLDER: Dashboard Screenshot]" alt="Progress Dashboard" width="30%" />
    <img src="[PLACEHOLDER: Journal Screenshot]" alt="Focus Journal" width="30%" />
</div>

<div style="display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 20px;">
    <img src="[PLACEHOLDER: Group Feature Screenshot]" alt="Heist Group Collaboration" width="30%" />
    <img src="[PLACEHOLDER: AI Summary Screenshot]" alt="AI-Generated Summary" width="30%" />
    <img src="[PLACEHOLDER: Settings Screenshot]" alt="Application Settings" width="30%" />
</div>

### Quick Start Guide

**Environment Requirements:**
- Android Studio Hedgehog | 2023.1.1 or higher
- Kotlin 1.9.0+
- Minimum SDK: 26 (Android 8.0)
- Target SDK: 34 (Android 14)

**Running Steps:**

1. Clone the repository:
```bash
git clone https://github.com/Hugooooooo526/CP3406-Mobile-Computing-for-Jcu.git
cd CP3406-Mobile-Computing-for-Jcu
```

2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Connect an Android device or launch an emulator
5. Click the Run ▶️ button to build and deploy

---

## 1. Problem Statement

### 1.1 Time Management and Focus Challenges

#### 1.1.1 Difficulty Maintaining Sustained Focus

- University students frequently experience fragmented attention spans, with studies showing that the average student's focused work period lasts only 12-15 minutes before interruption (Mark et al., 2016)
- Research indicates that 73% of students report difficulty maintaining concentration during study sessions, leading to decreased academic performance and increased stress levels (Kirschner & Karpinski, 2010)
- Digital distractions and multitasking behaviors result in an estimated 40% reduction in productive study time among undergraduate students (Rosen et al., 2013)

#### 1.1.2 Lack of Self-Awareness in Study Patterns

- Approximately 68% of students cannot accurately estimate the time they spend on academic tasks, leading to poor time allocation and planning (Häfner et al., 2014)
- Without systematic tracking mechanisms, students struggle to identify their most productive hours and optimal study conditions (Misra & McKean, 2000)
- The absence of reflection practices prevents students from recognizing and addressing ineffective study habits that persist throughout their academic career (Zimmerman, 2008)

### 1.2 Motivation and Accountability Issues

#### 1.2.1 Insufficient Progress Visualization

- Students often fail to recognize incremental progress in their study efforts, which diminishes motivation and contributes to academic procrastination affecting 80-95% of college students (Steel, 2007)
- Research demonstrates that visible progress indicators can increase task persistence by up to 35% in educational contexts (Schunk & Ertmer, 2000)
- The lack of tangible feedback on study consistency leads to decreased self-efficacy and academic engagement (Bandura, 1997)

#### 1.2.2 Limited Social Support and Collaboration

- Individual study approaches often result in isolation, with 62% of students reporting decreased motivation when studying alone for extended periods (Dennis et al., 2005)
- Peer accountability mechanisms have been shown to improve study consistency by 45% and enhance learning outcomes through collaborative motivation (Tuckman, 2003)
- Students without structured group support systems are more likely to abandon their study goals prematurely (Locke & Latham, 2002)

---

## 2. Solution Overview

Our mobile application aims to help university students manage their focus time effectively by providing:

### 2.1 Structured Focus Management System

- Pomodoro-style timer with customizable durations (5-120 minutes) and automated break scheduling
- iOS-inspired time picker interface for intuitive session configuration
- Stopwatch mode for flexible, unstructured study sessions
- Visual and audio notifications to guide users through focus and break periods
- Pause, resume, and reset controls for flexible session management

### 2.2 Self-Reflection and Learning Journal

- Post-session emotional state tracking with mood selection interface
- Structured note-taking capability for recording learning insights and observations
- Historical journal entries with browsing and editing functionality
- Categorization system for academic versus personal focus sessions
- Data persistence for long-term reflection and pattern analysis

### 2.3 Progress Visualization Dashboard

- Daily and weekly focus time statistics with visual charts and graphs
- Streak tracking system to measure study consistency
- Academic versus personal time ratio analysis with color-coded indicators
- Date range filtering for customized progress reports
- Achievement milestones and progress indicators

### 2.4 Collaborative Motivation Features

- Heist Group system supporting 3-5 member study teams
- Shared goal setting and collective progress tracking
- Member contribution visibility to foster accountability
- Group challenges and milestone celebrations
- Real-time synchronization of group member activities

### 2.5 AI-Powered Insights and Recommendations

- Weekly and monthly study pattern analysis using Gemini API integration
- Personalized productivity recommendations based on historical data
- Automated summary generation of focus sessions and achievements
- Trend identification and actionable insights for study optimization
- Natural language reports highlighting strengths and improvement areas

---

## 3. Key Features

### 3.1 Focus Timer Module

- Users can configure session duration through an iOS-style scrollable time picker ranging from 5 to 120 minutes
- The timer interface displays remaining time with clear visual indicators and progress animations
- Automated break initiation follows completed focus sessions, with configurable break durations
- Users can pause sessions to handle urgent interruptions and resume without losing progress
- Session completion triggers automatic journal entry prompts to capture immediate insights
- Sound effects and haptic feedback provide non-intrusive session state notifications

### 3.2 Focus Journal System

- Post-session mood selection interface featuring multiple emotional states (productive, neutral, distracted, stressed)
- Rich text note editor for recording learning observations, challenges encountered, and insights gained
- Session categorization as Academic or Personal for organized tracking
- Journal history view with chronological ordering and search functionality
- Entry editing and deletion capabilities with confirmation dialogs
- Export functionality for external backup and analysis

### 3.3 Progress Dashboard

- Real-time display of today's accumulated focus minutes with daily goal comparison
- Weekly progress chart showing focus time distribution across seven days
- Streak counter measuring consecutive days of focus activity with milestone celebrations
- Pie chart visualization of academic versus personal time allocation
- Date range selector for viewing historical progress data
- Statistical summaries including total sessions, average session length, and completion rate

### 3.4 Heist Group Collaboration

- Group creation interface with customizable names and goal descriptions
- Member invitation system supporting 3-5 participants per group
- Collective progress tracking showing each member's contribution to shared goals
- Group activity feed displaying recent member completions and milestones
- Achievement badges for meeting group targets and maintaining consistency
- In-app messaging for coordination and motivation (TODO)

### 3.5 AI Summary Generation

- One-click generation of weekly productivity summaries analyzing study patterns
- Monthly comprehensive reports identifying trends, peak performance times, and areas for improvement
- Natural language recommendations tailored to individual study habits
- Integration with Gemini API for advanced language model capabilities
- Customizable report parameters and focus areas
- Report history with comparison features to track improvement over time

### 3.6 Enhancement Features

- Dark and light theme toggle with system-default detection and manual override
- Nature-themed sound effects library including forest ambience, ocean waves, rainfall, and stream sounds
- Background music player with volume control and automatic pause during breaks
- Multi-language support for English and Simplified Chinese with in-app language switcher
- Accessibility features including high contrast mode, larger text options, and screen reader compatibility

---

## 4. Technical Implementation

The application is built using:

- **Kotlin** as the primary programming language for Android native development, ensuring type safety and modern language features
- **Jetpack Compose** for declarative UI development, enabling reactive interfaces and simplified state management
- **Material Design 3** components and theming system for consistent, accessible, and modern user interface design
- **MVVM (Model-View-ViewModel)** architecture pattern to separate concerns and enhance testability
- **Room Persistence Library** for local data storage, providing robust SQLite database management with compile-time query verification
- **Jetpack Navigation Compose** for type-safe screen navigation and deep linking capabilities
- **Kotlin Coroutines and Flow** for asynchronous operations and reactive data streams
- **Hilt dependency injection** framework for managing application dependencies and improving code modularity
- **Gemini API** integration for AI-powered text generation and productivity analysis
- **MediaPlayer API** for sound effects and background music playback with lifecycle-aware management

---

## 5. Expected Impact

By implementing these features, we aim to help university students:

- **Improve focus quality and duration** by providing structured time management tools that reduce distractions and promote sustained concentration during study sessions
- **Increase self-awareness** of study patterns, productivity peaks, and personal learning preferences through comprehensive tracking and visualization capabilities
- **Maintain consistent study habits** via streak tracking, progress visualization, and daily goal reminders that build long-term behavioral change
- **Reduce academic stress and anxiety** by breaking large study tasks into manageable Pomodoro sessions with built-in rest periods
- **Enhance peer accountability and motivation** through collaborative group features that leverage social support for sustained effort
- **Make data-driven decisions** about study strategies using AI-generated insights and personalized recommendations based on historical performance
- **Develop metacognitive skills** through regular journal reflection that encourages thinking about learning processes and effectiveness

---

## 6. Conclusion

Our mobile application, FocusGarden, is designed to address the multifaceted focus management and productivity challenges faced by university students in their academic pursuits. By leveraging modern Android development technologies including Kotlin, Jetpack Compose, and Material Design 3, combined with AI-powered analysis through Gemini API integration, we provide a comprehensive and user-friendly solution that helps students structure their study time, reflect on their learning experiences, visualize their progress, and collaborate with peers for mutual motivation. Through systematic implementation of evidence-based productivity techniques and thoughtful user experience design, FocusGarden aims to transform how students approach their academic work and develop sustainable focus habits for long-term success.

---

## 7. References

- Bandura, A. (1997). Self-efficacy: The exercise of control. W.H. Freeman and Company.
- Dennis, J. M., Phinney, J. S., & Chuateco, L. I. (2005). The role of motivation, parental support, and peer support in the academic success of ethnic minority first-generation college students. Journal of College Student Development, 46(3), 223-236. https://doi.org/10.1353/csd.2005.0023
- Häfner, A., Stock, A., & Oberst, V. (2014). Decreasing students' stress through time management training: An intervention study. European Journal of Psychology of Education, 30(1), 81-94. https://doi.org/10.1007/s10212-014-0229-2
- Kirschner, P. A., & Karpinski, A. C. (2010). Facebook and academic performance. Computers in Human Behavior, 26(6), 1237-1245. https://doi.org/10.1016/j.chb.2010.03.024
- Locke, E. A., & Latham, G. P. (2002). Building a practically useful theory of goal setting and task motivation. American Psychologist, 57(9), 705-717. https://doi.org/10.1037/0003-066X.57.9.705
- Mark, G., Iqbal, S. T., Czerwinski, M., Johns, P., & Sano, A. (2016). Email duration, batching and self-interruption: Patterns of email use on productivity and stress. Proceedings of the 2016 CHI Conference on Human Factors in Computing Systems, 1717-1728. https://doi.org/10.1145/2858036.2858262
- Misra, R., & McKean, M. (2000). College students' academic stress and its relation to their anxiety, time management, and leisure satisfaction. American Journal of Health Studies, 16(1), 41-51.
- Rosen, L. D., Carrier, L. M., & Cheever, N. A. (2013). Facebook and texting made me do it: Media-induced task-switching while studying. Computers in Human Behavior, 29(3), 948-958. https://doi.org/10.1016/j.chb.2012.12.001
- Schunk, D. H., & Ertmer, P. A. (2000). Self-regulation and academic learning: Self-efficacy enhancing interventions. In M. Boekaerts, P. R. Pintrich, & M. Zeidner (Eds.), Handbook of self-regulation (pp. 631-649). Academic Press.
- Steel, P. (2007). The nature of procrastination: A meta-analytic and theoretical review of quintessential self-regulatory failure. Psychological Bulletin, 133(1), 65-94. https://doi.org/10.1037/0033-2909.133.1.65
- Tuckman, B. W. (2003). The effect of learning and motivation strategies training on college students' achievement. Journal of College Student Development, 44(3), 430-437. https://doi.org/10.1353/csd.2003.0034
- Zimmerman, B. J. (2008). Investigating self-regulation and motivation: Historical background, methodological developments, and future prospects. American Educational Research Journal, 45(1), 166-183. https://doi.org/10.3102/0002831207312909

---

## 8. Acknowledgements

I would like to thank the following individuals for their valuable contributions to the development of FocusGarden:

- **Dr. Lum Kum Meng** ([@Lum-KumMeng](https://github.com/Lum-KumMeng)) - Course Lecturer for CP3406 Mobile Computing at James Cook University Singapore, for guidance on mobile application architecture and development best practices. Email: maxlum78@hotmail.com
- **James Cook University Singapore** - For providing the educational framework and resources supporting this project
- **Google Gemini API Team** - For providing AI capabilities that enable intelligent productivity analysis
- **Android Development Community** - For comprehensive documentation and open-source libraries that facilitated rapid development

---

## Project Information

**Course:** CP3406 - Mobile Computing  
**Institution:** James Cook University Singapore  
**Developer:** Cui Langxuan (Hugo)  
**Student ID:** 14706438  
**Email:** langxuan.cui@my.jcu.edu.au  
**GitHub Repository:** [https://github.com/Hugooooooo526/CP3406-Mobile-Computing-for-Jcu](https://github.com/Hugooooooo526/CP3406-Mobile-Computing-for-Jcu)

**Project Status:** Completed (November 2025)  
**License:** Educational use only - JCU CP3406 Course Assignment

---

## Additional Documentation

For comprehensive technical specifications and development details, please refer to:

- [Technical Development Document](./docs/TechnicalDevelopmentDocument.md) - Complete architectural design and implementation details
- [Feature Enhancement Plan](./docs/Feature_Enhancement_Plan.md) - Week 5-6 enhancement features design documentation
- [Week 3-4 Progress Report](./docs/Week3-4_Progress_Report.md) - Development progress and milestone achievements

---

**Project Philosophy:**
> "Consistency grows your garden. Through continuous focus and effort, cultivate good study habits and let your garden of knowledge flourish."
