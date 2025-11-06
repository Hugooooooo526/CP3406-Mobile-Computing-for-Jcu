# 🔊 Sound Effects Guide

## Required Sound Files

Please download the following sound effects and place them in this directory (`app/src/main/res/raw/`).

### 1. sound_focus_start.mp3 (~0.5s)
**Description:** Gentle water drop sound  ✅
**Use:** Played when user starts a focus session  


**Ideal characteristics:**
- Duration: 1 seconds
- Format: MP3 or OGG
- Size: < 100KB
- Volume: Medium
- Natural, calming sound

### 2. sound_focus_pause.mp3 (~0.3s)✅
**Description:** Soft bamboo tap or light wood click  
**Use:** Played when user pauses a focus session  


**Ideal characteristics:**
- Duration: 1 seconds
- Format: MP3 or OGG
- Size: < 50KB
- Volume: Soft
- Short, non-intrusive

### 3. sound_focus_complete.mp3 (~1.5s)✅
**Description:** Bird chirp with gentle wind chime  
**Use:** Played when user completes a focus session  


**Ideal characteristics:**
- Duration: 3 seconds
- Format: MP3 or OGG
- Size: < 150KB
- Volume: Medium-high (celebratory but not loud)
- Encouraging, positive tone

### 4. sound_focus_cancel.mp3 (~0.4s)✅
**Description:** Light leaf rustle or soft swoosh  
**Use:** Played when user cancels a focus session  


**Ideal characteristics:**
- Duration: 1 seconds
- Format: MP3 or OGG
- Size: < 50KB
- Volume: Soft
- Neutral, non-negative tone

---

## Quick Download Guide

### Option 1: Freesound.org (Recommended)
1. Visit [freesound.org](https://freesound.org)
2. Search for the sound type (e.g., "water drop")
3. Filter by:
   - Duration: 0-2 seconds
   - License: CC0 or CC BY (attribution required)
4. Download the MP3/OGG file
5. Rename to match the required filename
6. Place in `app/src/main/res/raw/`

### Option 2: Zapsplat.com
1. Visit [zapsplat.com](https://www.zapsplat.com)
2. Free account required
3. Search for sound effects
4. Download and rename

### Option 3: Use Placeholder Sounds (Testing)
For testing purposes, you can use any short MP3 files and rename them to match the required names. The app will work with any audio file.

---

## File Naming Rules

⚠️ **Important:** Android resource files must:
- Be lowercase only
- Use underscores, not spaces or hyphens
- Not start with numbers
- Be in MP3 or OGG format

✅ **Correct:** `sound_focus_start.mp3`  
❌ **Wrong:** `Sound-Focus-Start.mp3`, `1_focus_start.mp3`

---

## Testing

After adding sound files:
1. Clean and rebuild the project in Android Studio
2. The sounds will be automatically loaded by `SoundManager.kt`
3. Test each sound:
   - Start Focus → should play water drop
   - Pause Focus → should play bamboo tap
   - Complete Focus → should play bird chirp + chime
   - Cancel Focus → should play leaf rustle

---

## Licensing

When using sounds from Freesound or other sources:
- Check the license (CC0, CC BY, etc.)
- If CC BY, add attribution in your app's About section
- Keep a record of sound sources for your documentation

---

## Alternative: AI-Generated Sounds

You can also use AI tools to generate custom sound effects:
- [ElevenLabs Sound Effects](https://elevenlabs.io/sound-effects)
- Other AI audio generation tools

This ensures unique sounds and no licensing concerns.

