package com.jcu.focusgarden.data.preferences

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.languageDataStore by preferencesDataStore(name = "language_preferences")

/**
 * LanguagePreferences
 * 
 * 管理应用的语言偏好设置
 * Week 5-6 Enhancement Feature #4: Multi-Language Support
 * 
 * 支持语言:
 * - "en" - English (默认)
 * - "zh" - 简体中文
 * 
 * @author Hugo Cui (14706438)
 * @since Week 5-6
 */
class LanguagePreferences(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_CHINESE = "zh"
    }

    /**
     * 当前选择的语言
     * Flow 类型，可以被 Compose 组件观察
     */
    val currentLanguage: Flow<String> = context.languageDataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: LANGUAGE_ENGLISH
        }

    /**
     * 切换到指定语言
     * 
     * @param languageCode 语言代码 ("en" 或 "zh")
     */
    suspend fun setLanguage(languageCode: String) {
        // 保存到 DataStore
        context.languageDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languageCode
        }
        
        // 立即应用语言更改
        applyLanguage(languageCode)
    }

    /**
     * 应用语言设置到系统
     * 
     * @param languageCode 语言代码
     */
    private fun applyLanguage(languageCode: String) {
        val localeTag = when (languageCode) {
            LANGUAGE_CHINESE -> "zh-CN"
            else -> "en-US"
        }
        
        val localeList = LocaleListCompat.forLanguageTags(localeTag)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    /**
     * 初始化语言设置
     * 在应用启动时调用，应用已保存的语言偏好
     */
    suspend fun initLanguage() {
        currentLanguage.collect { languageCode ->
            applyLanguage(languageCode)
            return@collect // 只执行一次
        }
    }
}

