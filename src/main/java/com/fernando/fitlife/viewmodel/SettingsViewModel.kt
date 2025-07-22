package com.fernando.fitlife.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = SettingsRepository(app)

    val darkMode: StateFlow<Boolean> = repo.darkModeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val autoDarkMode: StateFlow<Boolean> = repo.autoDarkModeFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val notifications: StateFlow<Boolean> = repo.notificationsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val animations: StateFlow<Boolean> = repo.animationsFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch {
        repo.setDarkMode(enabled)
    }

    fun setAutoDarkMode(enabled: Boolean) = viewModelScope.launch {
        repo.setAutoDarkMode(enabled)
        if (enabled) {
            aplicarTemaAutomaticoPorHorario()
        }
    }

    fun setNotifications(enabled: Boolean) = viewModelScope.launch {
        repo.setNotifications(enabled)
    }

    fun setAnimations(enabled: Boolean) = viewModelScope.launch {
        repo.setAnimations(enabled)
    }

    /**
     * Aplica automaticamente o modo escuro com base na hora atual do sistema:
     * Ativa entre 18h e 6h, desativa entre 6h e 18h.
     */
    fun aplicarTemaAutomaticoPorHorario() = viewModelScope.launch {
        val horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val usarModoEscuro = horaAtual < 6 || horaAtual >= 18
        repo.setDarkMode(usarModoEscuro)
    }
}
