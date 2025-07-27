package com.fernando.fitlife.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fernando.fitlife.ui.screens.*
import com.fernando.fitlife.ui.screens.auth.LoginScreen
import com.fernando.fitlife.ui.screens.auth.RegisterScreen
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.FavoritosPersonalViewModel
import com.fernando.fitlife.viewmodel.SettingsViewModel
import com.fernando.fitlife.viewmodel.AuthViewModel
import com.fernando.fitlife.viewmodel.TrainerViewModel
import com.fernando.fitlife.viewmodel.WorkoutsViewModel

@Composable
fun FitLifeNavGraph(
    navController: NavHostController,
    favoritosViewModel: FavoritosViewModel,
    favoritosPersonalViewModel: FavoritosPersonalViewModel,
    settingsViewModel: SettingsViewModel,
    authViewModel: AuthViewModel,
    trainerViewModel: TrainerViewModel,
    workoutsViewModel: WorkoutsViewModel
) {
    val startDestination = if (authViewModel.currentUser == null) "login" else "home"
    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }

        composable("register") {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }

        composable("trainer") {
            TrainerMenuScreen(
                navController = navController,
                trainerViewModel = trainerViewModel,
                authViewModel = authViewModel
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                authViewModel = authViewModel,
                workoutsViewModel = workoutsViewModel,
                trainerViewModel = trainerViewModel
            )
        }

        composable("detalhes/{treinoId}") { backStackEntry ->
            val treinoId = backStackEntry.arguments?.getString("treinoId") ?: ""
            DetalhesScreen(
                treinoId = treinoId,
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                workoutsViewModel = workoutsViewModel
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                favoritosPersonalViewModel = favoritosPersonalViewModel // ✅ Adicionado
            )
        }

        composable("configuracoes") {
            ConfiguracoesScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                settingsViewModel = settingsViewModel
            )
        }

        composable("personais") {
            PersonaisScreen(
                navController = navController,
                favoritosViewModel = favoritosPersonalViewModel,
                trainerViewModel = trainerViewModel
            )
        }

        composable("ajuda") {
            AjudaScreen(navController = navController)
        }
    }
}
