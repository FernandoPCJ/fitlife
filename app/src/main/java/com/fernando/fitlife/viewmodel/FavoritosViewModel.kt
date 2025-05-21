package com.fernando.fitlife.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.fernando.fitlife.model.Treino

class FavoritosViewModel : ViewModel() {

    // Lista observável de treinos favoritos
    private val _favoritos = mutableStateListOf<Treino>()
    val favoritos: List<Treino> get() = _favoritos

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

    // Remove todos os favoritos
    fun limparTodos() {
        _favoritos.clear()
    }
}
