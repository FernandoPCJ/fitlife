package com.fernando.fitlife.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore by preferencesDataStore(name = "settings")

object SettingsKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val NOTIFICATIONS = booleanPreferencesKey("notifications")
    val ANIMATIONS = booleanPreferencesKey("animations")
}

class SettingsRepository(private val context: Context) {
    val darkModeFlow: Flow<Boolean> = context.settingsDataStore.data
        .map { it[SettingsKeys.DARK_MODE] ?: false }

    val notificationsFlow: Flow<Boolean> = context.settingsDataStore.data
        .map { it[SettingsKeys.NOTIFICATIONS] ?: true }

    val animationsFlow: Flow<Boolean> = context.settingsDataStore.data
        .map { it[SettingsKeys.ANIMATIONS] ?: true }

    suspend fun setDarkMode(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.DARK_MODE] = enabled }
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.NOTIFICATIONS] = enabled }
    }

    suspend fun setAnimations(enabled: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.ANIMATIONS] = enabled }
    }
}