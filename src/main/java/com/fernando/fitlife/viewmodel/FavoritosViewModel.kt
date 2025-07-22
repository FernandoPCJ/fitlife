package com.fernando.fitlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.model.Treino
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf

class FavoritosViewModel : ViewModel() {

    // Lista observável de treinos favoritos
    private val _favoritos = mutableStateListOf<Treino>()
    val favoritos: List<Treino> get() = _favoritos

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Adiciona um treino à lista de favoritos
    fun adicionar(treino: Treino) {
        if (treino !in _favoritos) {
            _favoritos.add(treino)
        }
    }

    // Remove um treino da lista de favoritos
    fun remover(treino: Treino) {
        _favoritos.remove(treino)
    }

    // Verifica se um treino está na lista de favoritos
    fun isFavorito(treino: Treino): Boolean {
        return treino in _favoritos
    }

    // Remove todos os favoritos com feedback visual de carregamento
    fun limparTodosComLoading() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000) // Simulação de processo async
            _favoritos.clear()
            _isLoading.value = false
        }
    }
}
