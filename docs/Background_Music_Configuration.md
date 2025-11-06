# 🎵 Background Music System

## ✅ Status: Fully Implemented (Music Files Required)

All code for the background music feature has been implemented. You only need to add 4 music files to make it work.

---

## 📋 Overview

The background music system provides ambient sounds to help users focus during study sessions. It features:
- **4 Types of Music:** Rain, Ocean, Forest, Stream
- **Random Playback:** Automatically selects a track
- **Continuous Loop:** Music repeats seamlessly
- **Global Playback:** Continues playing across all screens
- **One-Button Control:** Simple play/stop interface
- **Foreground Service:** Keeps playing even when app is minimized

---

## 🏗️ Architecture

### Components

| Component | File | Purpose |
|-----------|------|---------|
| **Service** | `MusicPlayerService.kt` | Foreground service for background playback |
| **MainActivity** | `MainActivity.kt` | Service lifecycle management & binding |
| **DashboardScreen** | `DashboardScreen.kt` | Music control button (UI) |
| **Navigation** | `Navigation.kt` + `FocusGardenApp.kt` | Parameter passing |
| **Manifest** | `AndroidManifest.xml` | Permissions & service declaration |

### Flow Diagram

```
User clicks "Music" button in Dashboard
           ↓
MainActivity.startMusicService()
           ↓
MusicPlayerService starts
           ↓
Random music file selected
           ↓
MediaPlayer plays & loops
           ↓
Foreground notification appears
           ↓
User navigates to other screens
           ↓
Music continues playing
           ↓
User clicks "Stop" button
           ↓
MainActivity.stopMusicService()
           ↓
Service stops & notification disappears
```

---

## 🎨 User Interface

### Dashboard Quick Actions (2×2 Grid)

```
┌────────────────────────────────────┐
│  [ ▶️ Start ]    [ ✏️ Journal ]    │
│  [ 🤖 AI ]       [ 🎵 Music ]      │
└────────────────────────────────────┘
```

### Music Button States

| State | Icon | Text | Color | Behavior |
|-------|------|------|-------|----------|
| Stopped | 🎵 | "Music" | Primary | Click to start |
| Playing | ⏹️ | "Stop" | Secondary | Click to stop |

---

## 🔧 Technical Implementation

### 1. MusicPlayerService

**Type:** Foreground Service  
**Location:** `app/src/main/java/com/jcu/focusgarden/service/MusicPlayerService.kt`

**Key Features:**
- Uses Android `MediaPlayer` API for audio playback
- Runs as Foreground Service with notification
- Supports 4 music files (randomly selected)
- Loop mode enabled (`setLooping(true)`)
- Volume set to 70% for non-intrusive listening
- Graceful error handling

**Music Resources:**
```kotlin
private val musicList = listOf(
    R.raw.ambient_rain,
    R.raw.ambient_ocean,
    R.raw.ambient_forest,
    R.raw.ambient_stream
)
```

### 2. Service Lifecycle

**Binding:**
```kotlin
// MainActivity
private val musicConnection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicPlayerService.MusicBinder
        musicService = binder.getService()
        isMusicBound = true
        isMusicPlaying.value = musicService?.isPlaying() ?: false
    }
    
    override fun onServiceDisconnected(name: ComponentName?) {
        isMusicBound = false
        musicService = null
        isMusicPlaying.value = false
    }
}
```

**Start/Stop:**
```kotlin
private fun startMusicService() {
    val intent = Intent(this, MusicPlayerService::class.java).apply {
        action = MusicPlayerService.ACTION_PLAY
    }
    startService(intent)
    bindService(...)
    isMusicPlaying.value = true
}

private fun stopMusicService() {
    val intent = Intent(this, MusicPlayerService::class.java).apply {
        action = MusicPlayerService.ACTION_STOP
    }
    startService(intent)
    unbindService(musicConnection)
    isMusicPlaying.value = false
}
```

### 3. Permissions (AndroidManifest.xml)

```xml
<!-- Foreground Service 权限 (背景音乐播放) -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />

<!-- 通知权限 (Android 13+) -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### 4. Service Declaration

```xml
<!-- 背景音乐播放服务 -->
<service
    android:name=".service.MusicPlayerService"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="mediaPlayback">
    <intent-filter>
        <action android:name="com.jcu.focusgarden.ACTION_PLAY" />
        <action android:name="com.jcu.focusgarden.ACTION_STOP" />
    </intent-filter>
</service>
```

---

## 📁 Required Music Files

### File Locations

All music files must be placed in:
```
app/src/main/res/raw/
```

### File Specifications

| Filename | Type | Duration | Size | Format |
|----------|------|----------|------|--------|
| `ambient_rain.mp3` | Rain sounds | 5-10 min | 2-5 MB | MP3/OGG |
| `ambient_ocean.mp3` | Ocean waves | 5-10 min | 2-5 MB | MP3/OGG |
| `ambient_forest.mp3` | Forest birds | 5-10 min | 2-5 MB | MP3/OGG |
| `ambient_stream.mp3` | Stream water | 5-10 min | 2-5 MB | MP3/OGG |

### How to Add Music Files

**See detailed guide:** `app/src/main/res/raw/BACKGROUND_MUSIC_README.md`

**Quick Steps:**
1. Download 4 ambient music files (see sources in README)
2. Rename to match required filenames
3. Place in `app/src/main/res/raw/` folder
4. Delete `.txt` placeholder files
5. Clean and rebuild project

---

## 🧪 Testing Guide

### Build and Run

```bash
# In Android Studio:
1. Add 4 music files to /app/src/main/res/raw/
2. Build → Clean Project
3. Build → Rebuild Project
4. Run → Run 'app' (▶️)
```

### Test Scenarios

**Test 1: Start Music**
1. Open app, navigate to Dashboard
2. Click "🎵 Music" button
3. ✅ Button changes to "⏹️ Stop"
4. ✅ Music starts playing
5. ✅ Notification appears: "🎵 Focus Music Playing"

**Test 2: Music Continues Across Screens**
1. While music is playing
2. Navigate to Timer screen → Music continues
3. Navigate to Heist screen → Music continues
4. Navigate to AI Summary → Music continues
5. ✅ Music never stops while navigating

**Test 3: Stop Music**
1. Click "⏹️ Stop" button
2. ✅ Music stops
3. ✅ Button changes back to "🎵 Music"
4. ✅ Notification disappears

**Test 4: Random Selection**
1. Start music → Note which track plays
2. Stop music
3. Start music again → Different track should play
4. Repeat multiple times → All 4 tracks should appear

**Test 5: Background Playback**
1. Start music
2. Press Home button (minimize app)
3. ✅ Music continues playing
4. ✅ Notification remains visible
5. Tap notification → Returns to app

**Test 6: App Restart**
1. Start music
2. Close app completely (swipe away from recent apps)
3. ✅ Music stops (expected behavior)
4. Reopen app → Music button shows "🎵 Music" (stopped state)

---

## 🎯 Notification System

### Notification Content

```
┌──────────────────────────────────────┐
│ 🎵 Focus Music Playing              │
│ Rain Sounds 🌧️                      │
│                          [Stop]      │
└──────────────────────────────────────┘
```

**Channel:** Low priority (non-intrusive)  
**Actions:** Stop button  
**Behavior:** Cannot be dismissed by swiping

### Notification Features

- **Ongoing:** User must stop music to dismiss
- **Silent:** No sound/vibration on appearance
- **Low Priority:** Won't interrupt user
- **Custom Icon:** Uses app launcher icon
- **Tap Action:** Opens app to Dashboard
- **Stop Action:** Stops music directly

---

## 🐛 Troubleshooting

### Problem: Compilation Error "resource not found"

**Cause:** Music files not added  
**Solution:**
- Add 4 MP3 files to `/app/src/main/res/raw/`
- File names must match exactly: `ambient_rain.mp3`, etc.
- Clean and rebuild project

### Problem: Music doesn't play

**Check:**
1. Device volume (media volume, not ringer)
2. Files are valid MP3 format
3. Logcat: `adb logcat | grep MusicPlayerService`

**Common Issues:**
- File format incompatible → Try different MP3
- File size too large → Compress to < 5MB
- Corrupted file → Re-download

### Problem: Music stops when switching screens

**This should NOT happen.** If it does:
1. Check service is running: `adb shell dumpsys activity services | grep MusicPlayerService`
2. Check notification is visible
3. Review logs for service crashes

### Problem: Notification doesn't appear

**Cause:** Android 13+ notification permission  
**Solution:** App will request permission on first music play (automatic)

**Manual Fix:**
- Settings → Apps → FocusGarden → Notifications → Enable

### Problem: Music doesn't stop

**Force Stop:**
1. Open notification drawer
2. Click "Stop" in notification
3. Or: Settings → Apps → FocusGarden → Force Stop

---

## 📊 Performance Impact

| Metric | Impact | Details |
|--------|--------|---------|
| **APK Size** | +8-20 MB | 4 music files |
| **Memory** | +10-20 MB | MediaPlayer runtime |
| **Battery** | Low | Optimized for background |
| **CPU** | Minimal | Hardware-accelerated decode |
| **Network** | None | Offline playback |

**Optimization:**
- Music files loop → No repeated loading
- Low bitrate (128 kbps) sufficient for ambient sounds
- Foreground service → System prioritizes playback
- Volume at 70% → Reduces power consumption

---

## 🔄 How to Update Music

### Replace Existing Music

1. Navigate to `app/src/main/res/raw/`
2. Delete old file (e.g., `ambient_rain.mp3`)
3. Add new file with same name
4. Clean and rebuild project
5. No code changes needed!

### Add More Music Types

To add additional music beyond the current 4:

1. **Add file:** Place new file in `/app/src/main/res/raw/`
   - Example: `ambient_fireplace.mp3`

2. **Update MusicPlayerService.kt:**
```kotlin
private val musicList = listOf(
    R.raw.ambient_rain,
    R.raw.ambient_ocean,
    R.raw.ambient_forest,
    R.raw.ambient_stream,
    R.raw.ambient_fireplace  // New addition
)

private val musicNames = listOf(
    "Rain Sounds 🌧️",
    "Ocean Waves 🌊",
    "Forest Birds 🌲",
    "Stream Water 💧",
    "Fireplace Crackle 🔥"  // New addition
)
```

3. Rebuild project

---

## 🎓 Learning Resources

### Recommended Music Sources

1. **Freesound.org** - High quality, free ambient sounds
2. **YouTube Audio Library** - No account needed
3. **Pixabay Music** - Free for commercial use
4. **Incompetech** - Royalty-free music

### Android Development References

- [MediaPlayer API](https://developer.android.com/reference/android/media/MediaPlayer)
- [Foreground Services](https://developer.android.com/guide/components/foreground-services)
- [Notifications](https://developer.android.com/develop/ui/views/notifications)

---

## ✅ Implementation Checklist

- [x] MusicPlayerService created
- [x] Foreground Service configured
- [x] Permissions added to manifest
- [x] Service declared in manifest
- [x] MainActivity service binding implemented
- [x] Start/stop functions implemented
- [x] UI button added to Dashboard
- [x] Button state management (Music ↔ Stop)
- [x] Navigation parameter passing
- [x] Notification system
- [x] Random music selection
- [x] Loop functionality
- [x] Error handling
- [ ] Music files added (user task)

---

## 📝 Code Statistics

| Component | Lines of Code | Complexity |
|-----------|--------------|------------|
| MusicPlayerService.kt | ~250 | Medium |
| MainActivity changes | ~60 | Low |
| DashboardScreen changes | ~100 | Low |
| Navigation changes | ~20 | Low |
| **Total** | **~430** | **Medium** |

---

**Last Updated:** 2025-11-06  
**Status:** ✅ Code Complete (Music Files Required)  
**Maintainer:** Hugo Cui (14706438)

