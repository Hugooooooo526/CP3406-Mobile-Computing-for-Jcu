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
 * ThemePreferences - 管理应用主题偏好设置
 * 使用 DataStore 持久化保存用户的主题选择
 */

// DataStore 扩展属性
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

class ThemePreferences(private val context: Context) {
    
    companion object {
        // 主题偏好键
        private val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }
    
    /**
     * 获取当前主题设置
     * @return Flow<Boolean> true = 深色主题, false = 浅色主题
     */
    val isDarkTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_THEME] ?: false // 默认浅色主题
    }
    
    /**
     * 保存主题设置
     * @param isDark true = 深色主题, false = 浅色主题
     */
    suspend fun setDarkTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDark
        }
    }
    
    /**
     * 切换主题（浅色 ↔ 深色）
     */
    suspend fun toggleTheme() {
        context.dataStore.edit { preferences ->
            val currentTheme = preferences[IS_DARK_THEME] ?: false
            preferences[IS_DARK_THEME] = !currentTheme
        }
    }
}

