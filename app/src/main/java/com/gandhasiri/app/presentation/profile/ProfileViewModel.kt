package com.gandhasiri.app.presentation.profile

import androidx.lifecycle.ViewModel
import com.gandhasiri.app.domain.repository.AuthRepository
import com.gandhasiri.app.domain.repository.SettingsRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    val isDarkMode = settingsRepository.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val languageCode = settingsRepository.languageCode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "en"
    )

    fun setDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkMode(isDark)
        }
    }

    fun setLanguageCode(languageCode: String) {
        viewModelScope.launch {
            settingsRepository.setLanguageCode(languageCode)
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
