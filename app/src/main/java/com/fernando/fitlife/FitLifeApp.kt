package com.fernando.fitlife.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fernando.fitlife.navigation.FitLifeNavGraph
import com.fernando.fitlife.ui.theme.FitLifeTheme
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.FavoritosPersonalViewModel
import com.fernando.fitlife.viewmodel.SettingsViewModel

@Composable
fun FitLifeApp(
    favoritosViewModel: FavoritosViewModel,
    favoritosPersonalViewModel: FavoritosPersonalViewModel, // ✅ Adicionado
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()
    val isDarkTheme by settingsViewModel.darkMode.collectAsState()

    FitLifeTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FitLifeNavGraph(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                favoritosPersonalViewModel = favoritosPersonalViewModel, // ✅ Adicionado
                settingsViewModel = settingsViewModel
            )
        }
    }
}
