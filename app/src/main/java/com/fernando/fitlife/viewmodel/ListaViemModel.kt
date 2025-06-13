package com.fernando.fitlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Representação de um item
data class Item(val id: Int, val nome: String)

class ListaViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _itens = MutableStateFlow<List<Item>>(emptyList())
    val itens: StateFlow<List<Item>> = _itens

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        carregarDados()
    }

    private fun carregarDados() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                delay(2000) // Simula carregamento de dados

                // Aqui você pode substituir pela lógica real para carregar dados
                val dados = listOf(
                    Item(1, "Item 1"),
                    Item(2, "Item 2"),
                    Item(3, "Item 3")
                )

                _itens.value = dados
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = "Erro ao carregar dados"
                _isLoading.value = false
            }
        }
    }
}
