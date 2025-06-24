package com.fernando.fitlife.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PreferenciasViewModel : ViewModel() {

    private val _notificacoesAtivas = mutableStateOf(true)
    val notificacoesAtivas: State<Boolean> get() = _notificacoesAtivas

    fun toggleNotificacoes() {
        _notificacoesAtivas.value = !_notificacoesAtivas.value
    }

    fun redefinir() {
        _notificacoesAtivas.value = true
    }
}
