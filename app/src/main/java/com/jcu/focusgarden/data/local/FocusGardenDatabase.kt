package com.jcu.focusgarden.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jcu.focusgarden.data.local.converter.StringListConverter
import com.jcu.focusgarden.data.local.dao.GroupDao
import com.jcu.focusgarden.data.local.dao.JournalDao
import com.jcu.focusgarden.data.local.dao.SessionDao
import com.jcu.focusgarden.data.local.entity.GroupEntity
import com.jcu.focusgarden.data.local.entity.JournalEntity
import com.jcu.focusgarden.data.local.entity.SessionEntity

/**
 * FocusGarden Database
 * 按照 TD 文档第 5 节定义
 * 
 * Room 数据库配置类，包含所有实体和 DAO
 */
@Database(
    entities = [
        SessionEntity::class,
        JournalEntity::class,
        GroupEntity::class
    ],
    version = 2,  // 增加版本号：添加了 sessionId 索引
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class FocusGardenDatabase : RoomDatabase() {
    
    abstract fun sessionDao(): SessionDao
    abstract fun journalDao(): JournalDao
    abstract fun groupDao(): GroupDao
    
    companion object {
        @Volatile
        private var INSTANCE: FocusGardenDatabase? = null
        
        fun getDatabase(context: Context): FocusGardenDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FocusGardenDatabase::class.java,
                    "focus_garden_database"
                )
                .fallbackToDestructiveMigration()  // 开发阶段：删除旧数据并重建
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}





