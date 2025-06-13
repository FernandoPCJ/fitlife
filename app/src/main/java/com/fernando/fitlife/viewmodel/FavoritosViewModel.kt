// FavoritosViewModel.kt
package com.fernando.fitlife.viewmodel

import androidx.lifecycle.ViewModel
import com.fernando.fitlife.model.Treino
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritosViewModel : ViewModel() {
    private val _favoritos = MutableStateFlow<List<Treino>>(emptyList())
    val favoritos: StateFlow<List<Treino>> = _favoritos

    fun isFavorito(treino: Treino): Boolean {
        return _favoritos.value.any { it.id == treino.id }
    }

    fun adicionar(treino: Treino) {
        if (!isFavorito(treino)) {
            _favoritos.value = _favoritos.value + treino
        }
    }

    fun remover(treino: Treino) {
        _favoritos.value = _favoritos.value.filter { it.id != treino.id }
    }

    fun limparTodos() {
        _favoritos.value = emptyList()
    }
}