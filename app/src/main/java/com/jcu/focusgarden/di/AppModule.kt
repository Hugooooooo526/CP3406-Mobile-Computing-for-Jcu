package com.jcu.focusgarden.di

import android.content.Context
import com.jcu.focusgarden.data.local.FocusGardenDatabase
import com.jcu.focusgarden.data.local.dao.SessionDao
import com.jcu.focusgarden.data.local.dao.JournalDao
import com.jcu.focusgarden.data.local.dao.GroupDao
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.data.repository.JournalRepository
import com.jcu.focusgarden.data.repository.GroupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module for Dependency Injection
 * Provides application-level dependencies
 * 
 * Week 9 Enhancement: Complete DI setup for better architecture
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides Room Database instance
     * Singleton to ensure single database instance across app
     */
    @Provides
    @Singleton
    fun provideFocusGardenDatabase(@ApplicationContext context: Context): FocusGardenDatabase {
        return FocusGardenDatabase.getDatabase(context)
    }

    /**
     * Provides SessionDao from database
     */
    @Provides
    @Singleton
    fun provideSessionDao(database: FocusGardenDatabase): SessionDao {
        return database.sessionDao()
    }

    /**
     * Provides JournalDao from database
     */
    @Provides
    @Singleton
    fun provideJournalDao(database: FocusGardenDatabase): JournalDao {
        return database.journalDao()
    }

    /**
     * Provides SessionRepository with injected dao
     */
    @Provides
    @Singleton
    fun provideSessionRepository(sessionDao: SessionDao): SessionRepository {
        return SessionRepository(sessionDao)
    }

    /**
     * Provides JournalRepository with injected dao
     */
    @Provides
    @Singleton
    fun provideJournalRepository(journalDao: JournalDao): JournalRepository {
        return JournalRepository(journalDao)
    }

    /**
     * Provides GroupDao from database
     */
    @Provides
    @Singleton
    fun provideGroupDao(database: FocusGardenDatabase): GroupDao {
        return database.groupDao()
    }

    /**
     * Provides GroupRepository with injected dao (for Heist feature)
     */
    @Provides
    @Singleton
    fun provideGroupRepository(groupDao: GroupDao): GroupRepository {
        return GroupRepository(groupDao)
    }
}
