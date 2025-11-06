package com.jcu.focusgarden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jcu.focusgarden.ui.navigation.FocusGardenApp
import com.jcu.focusgarden.ui.theme.FocusGardenTheme

/**
 * Main Activity
 * FocusGarden 应用的入口点
 * 
 * 按照 TD 文档 Week 3-4 实现：UI Wireframes
 * 所有屏幕已完成静态布局，等待 Week 5-6 添加业务逻辑
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FocusGardenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FocusGardenApp()
                }
            }
        }
    }
}

