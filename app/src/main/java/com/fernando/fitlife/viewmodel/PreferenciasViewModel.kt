package com.fernando.fitlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.repository.PreferenciasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferenciasViewModel(
    private val preferenciasRepository: PreferenciasRepository
) : ViewModel() {

    val darkTheme: StateFlow<Boolean> = preferenciasRepository.darkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val notificacoesAtivas: StateFlow<Boolean> = preferenciasRepository.notificacoes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val animacoesAtivas: StateFlow<Boolean> = preferenciasRepository.animacoes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun toggleDarkMode() {
        viewModelScope.launch {
            preferenciasRepository.setDarkMode(!darkTheme.value)
        }
    }

    fun toggleNotificacoes() {
        viewModelScope.launch {
            preferenciasRepository.setNotificacoes(!notificacoesAtivas.value)
        }
    }

    fun toggleAnimacoes() {
        viewModelScope.launch {
            preferenciasRepository.setAnimacoes(!animacoesAtivas.value)
        }
    }

    fun redefinir() {
        viewModelScope.launch {
            preferenciasRepository.setDarkMode(false)
            preferenciasRepository.setNotificacoes(true)
            preferenciasRepository.setAnimacoes(true)
        }
    }
}