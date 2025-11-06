package com.jcu.focusgarden.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * SoundPreferences - 管理音效偏好设置
 * 使用 DataStore 持久化保存用户的音效开关状态
 */

// DataStore 扩展属性
private val Context.soundDataStore: DataStore<Preferences> by preferencesDataStore(name = "sound_preferences")

class SoundPreferences(private val context: Context) {
    
    companion object {
        // 音效静音键
        private val IS_SOUND_MUTED = booleanPreferencesKey("is_sound_muted")
    }
    
    /**
     * 获取当前音效状态
     * @return Flow<Boolean> true = 静音, false = 开启音效
     */
    val isSoundMuted: Flow<Boolean> = context.soundDataStore.data.map { preferences ->
        preferences[IS_SOUND_MUTED] ?: false // 默认开启音效
    }
    
    /**
     * 设置音效状态
     * @param isMuted true = 静音, false = 开启音效
     */
    suspend fun setSoundMuted(isMuted: Boolean) {
        context.soundDataStore.edit { preferences ->
            preferences[IS_SOUND_MUTED] = isMuted
        }
    }
    
    /**
     * 切换音效状态
     */
    suspend fun toggleSound() {
        context.soundDataStore.edit { preferences ->
            val currentMuted = preferences[IS_SOUND_MUTED] ?: false
            preferences[IS_SOUND_MUTED] = !currentMuted
        }
    }
}

