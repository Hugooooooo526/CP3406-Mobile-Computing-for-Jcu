package com.jcu.focusgarden.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jcu.focusgarden.MainActivity
import com.jcu.focusgarden.R

/**
 * MusicService - Background music player service
 * Use Foreground Service to ensure music continues to play in the background
 * 
 * Week 5-6 Enhancement Feature #3
 * 
 * Features:
 * - Play random white noise (rain sounds, ocean waves, forest sounds, stream sounds)
 * - Loop play
 * - Cross-page play
 * - Start/stop button
 */
class MusicService : Service() {
    
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentMusicIndex = -1
    
    // Music resource list
    // Note: Android automatically converts the hyphen (-) in the filename to an underscore (_)
    private val musicList = listOf(
        R.raw.copyright_free_rain_sounds_331497,    // 7 minutes
        R.raw.ocean_waves_sound_01_321570,          // 1:14 minutes
        R.raw.river_in_the_forest_17271,            // 3:20 minutes
        R.raw.water_small_stream_25614              // 37 seconds
    )
    
    // Music name (used for notification display)
    private val musicNames = listOf(
        "Rain Sounds 🌧️",
        "Ocean Waves 🌊",
        "River in Forest 🌲",
        "Stream Water 💧"
    )
    
    private val binder = MusicBinder()
    
    companion object {
        private const val TAG = "MusicService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "focus_music_channel"
        private const val CHANNEL_NAME = "Focus Music"
        
        // Service Actions
        const val ACTION_PLAY = "com.jcu.focusgarden.ACTION_PLAY"
        const val ACTION_STOP = "com.jcu.focusgarden.ACTION_STOP"
    }
    
    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MusicService created")
        createNotificationChannel()
    }
    
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                if (!isPlaying) {
                    playRandomMusic()
                }
            }
            ACTION_STOP -> {
                stopMusic()
            }
        }
        return START_STICKY
    }
    
    /**
     * Play random music
     */
    fun playRandomMusic() {
        try {
            // Randomly select a song (ensure it does not repeat the last one)
            val newIndex = if (musicList.size > 1) {
                var index: Int
                do {
                    index = musicList.indices.random()
                } while (index == currentMusicIndex && musicList.size > 1)
                index
            } else {
                0
            }
            
            currentMusicIndex = newIndex
            val musicResource = musicList[currentMusicIndex]
            
            // Release the previous MediaPlayer
            mediaPlayer?.release()
            
            // Create a new MediaPlayer
            mediaPlayer = MediaPlayer.create(this, musicResource)?.apply {
                isLooping = true // Loop play
                setVolume(0.7f, 0.7f) // Set volume to 70%
                
                setOnPreparedListener {
                    start()
                    this@MusicService.isPlaying = true
                    Log.d(TAG, "Music started: ${musicNames[currentMusicIndex]}")
                }
                
                setOnErrorListener { mp, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                    this@MusicService.isPlaying = false
                    false
                }
            }
            
            // Start the foreground service
            startForeground(NOTIFICATION_ID, createNotification())
            
        } catch (e: Exception) {
            Log.e(TAG, "Error playing music", e)
            isPlaying = false
        }
    }
    
    /**
     * Stop music
     */
    fun stopMusic() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                release()
            }
            mediaPlayer = null
            isPlaying = false
            currentMusicIndex = -1
            
            Log.d(TAG, "Music stopped")
            
            // Stop the foreground service
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping music", e)
        }
    }
    
    /**
     * Get play status
     */
    fun isPlaying(): Boolean = isPlaying
    
    /**
     * Get current music name
     */
    fun getCurrentMusicName(): String {
        return if (currentMusicIndex >= 0 && currentMusicIndex < musicNames.size) {
            musicNames[currentMusicIndex]
        } else {
            "No Music"
        }
    }
    
    /**
     * Create notification channel (Android 8.0+)
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW // Low importance, do not disturb user
            ).apply {
                description = "Background music for focus sessions"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    /**
     * Create foreground service notification
     */
    private fun createNotification(): Notification {
        // Click notification to open the app
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        // Stop button
        val stopIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🎵 Focus Music Playing")
            .setContentText(getCurrentMusicName())
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use app icon
            .setContentIntent(pendingIntent)
            .addAction(
                android.R.drawable.ic_media_pause,
                "Stop",
                stopPendingIntent
            )
            .setOngoing(true) // Not swipe-able
            .setSilent(true) // Silent notification
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}    
