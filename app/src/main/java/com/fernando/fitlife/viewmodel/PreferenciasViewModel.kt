package com.fernando.fitlife.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PreferenciasViewModel : ViewModel() {

    // 🔒 Encapsulamento: só a ViewModel pode modificar
    private val _darkTheme = mutableStateOf(false)
    val darkTheme: State<Boolean> get() = _darkTheme

    private val _notificacoesAtivas = mutableStateOf(true)
    val notificacoesAtivas: State<Boolean> get() = _notificacoesAtivas

    fun toggleDarkMode() {
        _darkTheme.value = !_darkTheme.value
    }

    fun toggleNotificacoes() {
        _notificacoesAtivas.value = !_notificacoesAtivas.value
    }

    fun redefinir() {
        _darkTheme.value = false
        _notificacoesAtivas.value = true
    }
}
