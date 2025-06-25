package com.fernando.fitlife.viewmodel

import androidx.lifecycle.ViewModel
import com.fernando.fitlife.model.Personal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritosPersonalViewModel : ViewModel() {

    private val _favoritos = MutableStateFlow<List<Personal>>(emptyList())
    val favoritos: StateFlow<List<Personal>> = _favoritos

    fun adicionarOuRemover(personal: Personal) {
        _favoritos.value = if (_favoritos.value.contains(personal)) {
            _favoritos.value - personal
        } else {
            _favoritos.value + personal
        }
    }

    fun limparTodos() {
        _favoritos.value = emptyList()
    }
}
