package com.jcu.focusgarden.domain.model

/**
 * Summary Model
 * 按照 TD 文档第 6 节定义
 * 
 * AI 生成的周/月总结数据模型
 */
data class Summary(
    /**
     * 总专注时长（分钟）
     */
    val totalTime: Int,
    
    /**
     * 平均每日时长（分钟）
     */
    val avgDaily: Int,
    
    /**
     * 高峰日
     */
    val peakDay: String,
    
    /**
     * 个性化建议
     */
    val recommendation: String,
    
    /**
     * 最长连续天数
     */
    val longestStreak: Int = 0,
    
    /**
     * 心情趋势
     */
    val moodTrend: List<String> = emptyList()
)






