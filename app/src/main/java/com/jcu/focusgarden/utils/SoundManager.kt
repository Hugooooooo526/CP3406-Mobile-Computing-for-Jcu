package com.jcu.focusgarden.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.jcu.focusgarden.R

/**
 * SoundManager - 管理应用音效播放
 * 使用 SoundPool 播放短音效，提供即时反馈
 * 
 * Week 5-6 Enhancement Feature #2
 */
class SoundManager(private val context: Context) {
    
    private var soundPool: SoundPool? = null
    private var isMuted: Boolean = false
    
    // 音效 ID
    private var soundStartId: Int = 0
    private var soundPauseId: Int = 0
    private var soundCompleteId: Int = 0
    private var soundCancelId: Int = 0
    
    // 音效是否加载成功的标记
    private var soundsLoaded = false
    
    companion object {
        private const val TAG = "SoundManager"
        private const val MAX_STREAMS = 4 // 最大同时播放数
        
        // 音量设置 (0.0 - 1.0)
        private const val VOLUME_NORMAL = 0.8f
        private const val VOLUME_COMPLETE = 1.0f // 完成音效音量稍大
    }
    
    init {
        initSoundPool()
        loadSounds()
    }
    
    /**
     * 初始化 SoundPool
     */
    private fun initSoundPool() {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME) // 游戏音效类型
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            
            soundPool = SoundPool.Builder()
                .setMaxStreams(MAX_STREAMS)
                .setAudioAttributes(audioAttributes)
                .build()
            
            // 监听加载完成
            soundPool?.setOnLoadCompleteListener { _, _, status ->
                if (status == 0) {
                    soundsLoaded = true
                    Log.d(TAG, "Sound loaded successfully")
                } else {
                    Log.e(TAG, "Failed to load sound, status: $status")
                }
            }
            
            Log.d(TAG, "SoundPool initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize SoundPool", e)
        }
    }
    
    /**
     * 加载音效资源
     * 实际音效文件：
     * - game-start-317318.mp3 → 开始专注音效 (1s)
     * - pause-piano-sound-40579.mp3 → 暂停专注音效 (1s)
     * - level-win-6416.mp3 → 完成专注音效 (3s)
     * - ui-beep-menu-negative-02-228338.mp3 → 取消专注音效 (1s)
     */
    private fun loadSounds() {
        try {
            soundPool?.let { pool ->
                // 加载实际的音效文件
                // Android 会自动将文件名中的连字符(-) 转换为下划线(_)
                
                soundStartId = tryLoadSound(pool, R.raw.game_start_317318)
                soundPauseId = tryLoadSound(pool, R.raw.pause_piano_sound_40579)
                soundCompleteId = tryLoadSound(pool, R.raw.level_win_6416)
                soundCancelId = tryLoadSound(pool, R.raw.ui_beep_menu_negative_02_228338)
                
                Log.d(TAG, "All sounds loaded successfully. IDs: start=$soundStartId, " +
                        "pause=$soundPauseId, complete=$soundCompleteId, cancel=$soundCancelId")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading sounds", e)
        }
    }
    
    /**
     * 尝试加载单个音效
     */
    private fun tryLoadSound(pool: SoundPool, resourceId: Int): Int {
        return try {
            val soundId = pool.load(context, resourceId, 1)
            if (soundId == 0) {
                Log.w(TAG, "Sound resource not found: $resourceId")
            }
            soundId
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load sound resource: $resourceId", e)
            0
        }
    }
    
    /**
     * 播放"开始专注"音效
     * 水滴声，柔和的开始提示
     */
    fun playStart() {
        playSound(soundStartId, VOLUME_NORMAL, "Start")
    }
    
    /**
     * 播放"暂停专注"音效
     * 竹子敲击声，短促的暂停提示
     */
    fun playPause() {
        playSound(soundPauseId, VOLUME_NORMAL, "Pause")
    }
    
    /**
     * 播放"完成专注"音效
     * 鸟鸣 + 风铃声，鼓励性的完成提示
     */
    fun playComplete() {
        playSound(soundCompleteId, VOLUME_COMPLETE, "Complete")
    }
    
    /**
     * 播放"取消专注"音效
     * 叶子沙沙声，轻柔的取消提示
     */
    fun playCancel() {
        playSound(soundCancelId, VOLUME_NORMAL, "Cancel")
    }
    
    /**
     * 通用播放方法
     */
    private fun playSound(soundId: Int, volume: Float, soundName: String) {
        if (isMuted) {
            Log.d(TAG, "$soundName sound is muted")
            return
        }
        
        if (soundId == 0) {
            Log.w(TAG, "$soundName sound not loaded (file may be missing)")
            return
        }
        
        try {
            soundPool?.play(
                soundId,
                volume,      // 左声道音量
                volume,      // 右声道音量
                1,           // 优先级
                0,           // 循环 (0 = 不循环)
                1.0f         // 播放速率
            )
            Log.d(TAG, "Playing $soundName sound")
        } catch (e: Exception) {
            Log.e(TAG, "Error playing $soundName sound", e)
        }
    }
    
    /**
     * 设置静音状态
     */
    fun setMuted(muted: Boolean) {
        isMuted = muted
        Log.d(TAG, "Sound ${if (muted) "muted" else "unmuted"}")
    }
    
    /**
     * 切换静音状态
     */
    fun toggleMute(): Boolean {
        isMuted = !isMuted
        Log.d(TAG, "Sound toggled to ${if (isMuted) "muted" else "unmuted"}")
        return isMuted
    }
    
    /**
     * 获取当前静音状态
     */
    fun isMuted(): Boolean = isMuted
    
    /**
     * 释放资源
     * 应在 Activity/Service 销毁时调用
     */
    fun release() {
        try {
            soundPool?.release()
            soundPool = null
            soundsLoaded = false
            Log.d(TAG, "SoundPool released")
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing SoundPool", e)
        }
    }
    
    /**
     * 测试所有音效（用于调试）
     */
    fun testAllSounds() {
        Log.d(TAG, "Testing all sounds...")
        playStart()
        
        // 延迟播放其他音效，避免同时播放
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            playPause()
        }, 1000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            playComplete()
        }, 2000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            playCancel()
        }, 3500)
    }
}

