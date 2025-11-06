# 🎵 Background Music Setup Guide

## Required Music Files

Please download 4 ambient music files and place them in this directory (`app/src/main/res/raw/`).

---

## 📋 File Requirements

### 1. ambient_rain.mp3
**Type:** Rain sound / rainfall ambience  
**Duration:** 5-10 minutes  
**Size:** ~2-5 MB  
**Description:** Gentle rain falling on leaves, medium intensity

**Recommended sources:**
- [Freesound: Rain](https://freesound.org/search/?q=rain+ambience&f=duration%3A%5B300+TO+600%5D)
- [YouTube Audio Library: Nature Sounds](https://www.youtube.com/audiolibrary/music)
- Search: "rain ambience" or "rainfall sounds"

---

### 2. ambient_ocean.mp3
**Type:** Ocean waves / sea sounds  
**Duration:** 5-10 minutes  
**Size:** ~2-5 MB  
**Description:** Gentle ocean waves, peaceful beach atmosphere

**Recommended sources:**
- [Freesound: Ocean Waves](https://freesound.org/search/?q=ocean+waves&f=duration%3A%5B300+TO+600%5D)
- [YouTube Audio Library: Ambience](https://www.youtube.com/audiolibrary/music)
- Search: "ocean waves" or "sea sounds"

---

### 3. ambient_forest.mp3
**Type:** Forest ambience / bird sounds  
**Duration:** 5-10 minutes  
**Size:** ~2-5 MB  
**Description:** Forest birds, gentle wind through trees

**Recommended sources:**
- [Freesound: Forest](https://freesound.org/search/?q=forest+ambience&f=duration%3A%5B300+TO+600%5D)
- [YouTube Audio Library: Nature](https://www.youtube.com/audiolibrary/music)
- Search: "forest ambience" or "birds chirping"

---

### 4. ambient_stream.mp3
**Type:** Stream water / creek sounds  
**Duration:** 5-10 minutes  
**Size:** ~2-5 MB  
**Description:** Flowing water, babbling brook

**Recommended sources:**
- [Freesound: Stream](https://freesound.org/search/?q=stream+water&f=duration%3A%5B300+TO+600%5D)
- [YouTube Audio Library: Nature Sounds](https://www.youtube.com/audiolibrary/music)
- Search: "stream water" or "babbling brook"

---

## 🎧 Audio Specifications

All music files should meet these specifications:

| Property | Recommended Value |
|----------|------------------|
| **Format** | MP3 or OGG |
| **Duration** | 5-10 minutes (will loop) |
| **Bitrate** | 128-192 kbps |
| **Sample Rate** | 44.1 kHz |
| **Channels** | Stereo (2.0) |
| **Size** | 2-5 MB per file |
| **Volume** | Normalized to -6dB |

**Total estimated size:** ~8-20 MB

---

## 📥 Quick Download Guide

### Option 1: Freesound.org (Best Quality, Free)

1. Visit [freesound.org](https://freesound.org)
2. Create free account (required for downloads)
3. Search for each type (rain, ocean, forest, stream)
4. Filter by:
   - Duration: 5-10 minutes
   - License: CC0 or CC BY
   - Quality: High
5. Download MP3 format
6. Rename to match required filenames
7. Place in `app/src/main/res/raw/`

### Option 2: YouTube Audio Library (No Account Needed)

1. Visit [YouTube Audio Library](https://www.youtube.com/audiolibrary/music)
2. Search for "Rain" / "Ocean" / "Forest" / "Stream"
3. Filter by Category: Ambient
4. Download files
5. Convert to MP3 if needed (use online converter)
6. Rename and place in folder

### Option 3: AI-Generated (Custom & Free)

Use AI audio generation tools:
- [ElevenLabs Sound Effects](https://elevenlabs.io/sound-effects)
- [Suno AI](https://www.suno.ai/)
- Prompt: "5 minute ambient rain sounds for meditation"

### Option 4: Use Placeholder Sounds (Testing Only)

For quick testing, you can use any 4 MP3 files and rename them:
```bash
cp any_audio1.mp3 ambient_rain.mp3
cp any_audio2.mp3 ambient_ocean.mp3
cp any_audio3.mp3 ambient_forest.mp3
cp any_audio4.mp3 ambient_stream.mp3
```

---

## 🔧 File Naming Rules

⚠️ **Important:** Android resource files must follow these rules:

- ✅ **Correct:** `ambient_rain.mp3`
- ❌ **Wrong:** `Ambient-Rain.mp3`, `ambient rain.mp3`, `1_rain.mp3`

**Rules:**
- Lowercase only
- Use underscores `_`, not spaces or hyphens
- No special characters
- Don't start with numbers
- MP3 or OGG extension

---

## 🎨 Music Selection Tips

### For Best Focus Experience:

1. **Rain Sounds**
   - Steady, consistent rainfall
   - Avoid thunder or heavy storms
   - Medium intensity (not too loud)

2. **Ocean Waves**
   - Gentle waves, not crashing surf
   - Peaceful beach atmosphere
   - Rhythmic but not distracting

3. **Forest Ambience**
   - Mix of bird songs and rustling leaves
   - Natural, organic sounds
   - Not too many individual bird calls

4. **Stream Water**
   - Smooth flowing water
   - Babbling brook, not waterfall
   - Consistent, soothing flow

---

## 📊 Expected Behavior

Once files are added:

1. **User clicks "🎵 Focus Music" button in Dashboard**
2. **App randomly selects one of 4 music files**
3. **Music plays and loops continuously**
4. **Button changes to "⏸️ Stop Music"**
5. **Music continues playing when switching screens**
6. **Notification appears showing current music**
7. **Click button again to stop music**

---

## 🧪 Testing After Adding Files

1. **Add all 4 files to this folder**
2. **Clean and rebuild project:**
   ```
   Build → Clean Project
   Build → Rebuild Project
   ```
3. **Run the app**
4. **Go to Dashboard**
5. **Click "🎵 Focus Music" button**
6. **You should hear one of the music files playing**
7. **Navigate to Timer/Heist/AI screens - music continues**
8. **Click "⏸️ Stop Music" to stop**

---

## 🐛 Troubleshooting

**Problem: Compilation error "resource not found"**
- Make sure all 4 files exist in `/app/src/main/res/raw/`
- Check file names match exactly (lowercase, underscores)
- Clean and rebuild project

**Problem: Music doesn't play**
- Check device volume (media volume)
- Check if file is valid MP3 format
- Look at Logcat: `adb logcat | grep MusicPlayerService`

**Problem: Music stutters or skips**
- File may be too large (try < 5MB)
- Reduce bitrate to 128 kbps
- Check file format compatibility

**Problem: Music stops when app closes**
- This is expected behavior
- Music only plays while app is running (foreground or background)
- Android may stop service if system needs resources

---

## 📄 Licensing

When downloading music:
- Check the license (CC0, CC BY, etc.)
- CC0 = Public domain, no attribution needed
- CC BY = Attribution required
- Keep a record of sources for documentation

**Example Attribution:**
```
Rain sound by [Author] (freesound.org) - CC BY 4.0
```

---

## 💡 Pro Tips

1. **Loop Quality:** Choose files that loop seamlessly
2. **Mix It Up:** Different music types help prevent monotony
3. **File Size:** Smaller files = faster loading, less storage
4. **Quality Balance:** 128 kbps is sufficient for ambient sounds
5. **Test First:** Listen to each file before adding to app

---

## 🔄 How to Replace Music Later

To change any music file:
1. Delete old file from `/app/src/main/res/raw/`
2. Add new file with same name
3. Clean and rebuild project
4. No code changes needed!

---

**Status:** ⚠️ Setup Required  
**Priority:** Medium (Feature is coded, just needs music files)  
**Time to Setup:** 15-30 minutes

