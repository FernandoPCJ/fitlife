package com.fernando.fitlife.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repo = AuthRepository()
    val currentUser get() = repo.currentUser

    private val _role = MutableStateFlow<String?>(null)
    val role: StateFlow<String?> = _role

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _role.value = repo.login(email, password)
        }
    }

    fun register(
        email: String,
        password: String,
        trainer: Boolean,
        nome: String,
        especialidade: String? = null,
        descricao: String? = null,
        fotoUrl: String? = null
    ) {
        viewModelScope.launch {
            val success = repo.register(
                email,
                password,
                if (trainer) "trainer" else "client",
                nome,
                especialidade,
                descricao,
                fotoUrl
            )
            if (success) {
                _role.value = if (trainer) "trainer" else "client"
            }
        }
    }

    fun logout() {
        repo.logout()
        _role.value = null
    }

    fun loadRole() {
        val uid = repo.currentUser?.uid ?: return
        viewModelScope.launch {
            _role.value = repo.getRole(uid)
        }
    }
}
