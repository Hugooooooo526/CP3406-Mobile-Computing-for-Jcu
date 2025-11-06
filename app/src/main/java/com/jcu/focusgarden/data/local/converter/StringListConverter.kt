package com.jcu.focusgarden.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * String List Type Converter
 * 用于 Room 数据库存储 List<String>
 */
class StringListConverter {
    
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }
}





