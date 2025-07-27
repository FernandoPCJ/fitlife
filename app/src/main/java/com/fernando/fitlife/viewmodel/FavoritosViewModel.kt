package com.fernando.fitlife.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.repository.FavoritesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = FavoritesRepository(application)

    private var currentUserId: String? = null

    // Lista observável de treinos favoritos
    private val _favoritos = mutableStateListOf<Treino>()
    val favoritos: List<Treino> get() = _favoritos

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setUser(userId: String) {
        if (currentUserId == userId) return
        currentUserId = userId
        viewModelScope.launch {
            repo.getFavorites(userId).collectLatest { list ->
                _favoritos.clear()
                _favoritos.addAll(list)
            }
        }
    }

    // Adiciona um treino à lista de favoritos
    fun adicionar(treino: Treino) {
        val uid = currentUserId ?: return
        if (treino !in _favoritos) {
            viewModelScope.launch { repo.addFavorite(treino, uid) }
        }
    }

    // Remove um treino da lista de favoritos
    fun remover(treino: Treino) {
        viewModelScope.launch { repo.removeFavorite(treino) }
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
            val uid = currentUserId
            if (uid != null) {
                repo.clear(uid)
            }
            _favoritos.clear()
            _isLoading.value = false
        }
    }

    fun clearInMemory() {
        _favoritos.clear()
    }
}
