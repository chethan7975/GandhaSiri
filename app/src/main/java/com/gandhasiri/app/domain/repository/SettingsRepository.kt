package com.gandhasiri.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isDarkMode: Flow<Boolean>
    val languageCode: Flow<String>

    suspend fun setDarkMode(isDark: Boolean)
    suspend fun setLanguageCode(languageCode: String)
}
