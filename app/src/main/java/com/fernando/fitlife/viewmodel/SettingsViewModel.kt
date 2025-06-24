package com.fernando.fitlife.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = SettingsRepository(app)

    val darkMode: StateFlow<Boolean> = repo.darkModeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val notifications: StateFlow<Boolean> = repo.notificationsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val animations: StateFlow<Boolean> = repo.animationsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch {
        repo.setDarkMode(enabled)
    }

    fun setNotifications(enabled: Boolean) = viewModelScope.launch {
        repo.setNotifications(enabled)
    }

    fun setAnimations(enabled: Boolean) = viewModelScope.launch {
        repo.setAnimations(enabled)
    }
}
