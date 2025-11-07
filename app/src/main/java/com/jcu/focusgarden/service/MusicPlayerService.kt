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
 * MusicPlayerService - 背景音乐播放服务
 * 使用 Foreground Service 确保音乐在后台持续播放
 * 
 * Week 5-6 Enhancement Feature #3
 * 
 * 功能：
 * - 随机播放白噪音（雨声、海浪、森林、溪流）
 * - 循环播放
 * - 跨页面播放
 * - 一键开始/停止
 */
class MusicPlayerService : Service() {
    
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentMusicIndex = -1
    
    // 音乐资源列表
    // 注意：Android 会自动将文件名中的连字符(-) 转换为下划线(_)
    private val musicList = listOf(
        R.raw.copyright_free_rain_sounds_331497,    // 7 minutes
        R.raw.ocean_waves_sound_01_321570,          // 1:14 minutes
        R.raw.river_in_the_forest_17271,            // 3:20 minutes
        R.raw.water_small_stream_25614              // 37 seconds
    )
    
    // 音乐名称（用于通知显示）
    private val musicNames = listOf(
        "Rain Sounds 🌧️",
        "Ocean Waves 🌊",
        "River in Forest 🌲",
        "Stream Water 💧"
    )
    
    private val binder = MusicBinder()
    
    companion object {
        private const val TAG = "MusicPlayerService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "focus_music_channel"
        private const val CHANNEL_NAME = "Focus Music"
        
        // Service Actions
        const val ACTION_PLAY = "com.jcu.focusgarden.ACTION_PLAY"
        const val ACTION_STOP = "com.jcu.focusgarden.ACTION_STOP"
    }
    
    inner class MusicBinder : Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MusicPlayerService created")
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
     * 播放随机音乐
     */
    fun playRandomMusic() {
        try {
            // 随机选择一首音乐（确保不重复上一首）
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
            
            // 释放之前的 MediaPlayer
            mediaPlayer?.release()
            
            // 创建新的 MediaPlayer
            mediaPlayer = MediaPlayer.create(this, musicResource)?.apply {
                isLooping = true // 循环播放
                setVolume(0.7f, 0.7f) // 设置音量为70%
                
                setOnPreparedListener {
                    start()
                    isPlaying = true
                    Log.d(TAG, "Music started: ${musicNames[currentMusicIndex]}")
                }
                
                setOnErrorListener { mp, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                    isPlaying = false
                    false
                }
            }
            
            // 启动前台服务
            startForeground(NOTIFICATION_ID, createNotification())
            
        } catch (e: Exception) {
            Log.e(TAG, "Error playing music", e)
            isPlaying = false
        }
    }
    
    /**
     * 停止音乐
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
            
            // 停止前台服务
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping music", e)
        }
    }
    
    /**
     * 获取播放状态
     */
    fun isPlaying(): Boolean = isPlaying
    
    /**
     * 获取当前音乐名称
     */
    fun getCurrentMusicName(): String {
        return if (currentMusicIndex >= 0 && currentMusicIndex < musicNames.size) {
            musicNames[currentMusicIndex]
        } else {
            "No Music"
        }
    }
    
    /**
     * 创建通知渠道（Android 8.0+）
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW // 低重要性，不打扰用户
            ).apply {
                description = "Background music for focus sessions"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    /**
     * 创建前台服务通知
     */
    private fun createNotification(): Notification {
        // 点击通知时打开应用
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        // 停止按钮
        val stopIntent = Intent(this, MusicPlayerService::class.java).apply {
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
            .setSmallIcon(R.drawable.ic_launcher_foreground) // 使用应用图标
            .setContentIntent(pendingIntent)
            .addAction(
                android.R.drawable.ic_media_pause,
                "Stop",
                stopPendingIntent
            )
            .setOngoing(true) // 不可滑动删除
            .setSilent(true) // 静默通知
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
        Log.d(TAG, "MusicPlayerService destroyed")
    }
}

