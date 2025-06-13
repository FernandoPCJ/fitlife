package com.fernando.fitlife.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "preferencias")

object PreferenciasKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")  // Nome para modo escuro
    val NOTIFICACOES = booleanPreferencesKey("notificacoes") // Nome para notificações
    val ANIMACOES = booleanPreferencesKey("animacoes") // Nome para animações
}

class PreferenciasRepository(private val context: Context) {

    // Propriedades de Flow para observação das preferências
    val darkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferenciasKeys.DARK_MODE] ?: false }

    val notificacoes: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferenciasKeys.NOTIFICACOES] ?: true }

    val animacoes: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferenciasKeys.ANIMACOES] ?: true }

    // Funções para gravar as preferências no DataStore
    suspend fun setDarkMode(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenciasKeys.DARK_MODE] = value
        }
    }

    suspend fun setNotificacoes(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenciasKeys.NOTIFICACOES] = value
        }
    }

    suspend fun setAnimacoes(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenciasKeys.ANIMACOES] = value
        }
    }

    // Opcional: Função para resetar todas as preferências para valores padrão
    suspend fun resetPreferences() {
        context.dataStore.edit { preferences ->
            preferences[PreferenciasKeys.DARK_MODE] = false
            preferences[PreferenciasKeys.NOTIFICACOES] = true
            preferences[PreferenciasKeys.ANIMACOES] = true
        }
    }
}
