package com.fernando.fitlife.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fernando.fitlife.ui.screens.*
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.PreferenciasViewModel
import com.fernando.fitlife.ui.screens.AssistenteScreen


@Composable
fun FitLifeNavGraph(
    navController: NavHostController,
    favoritosViewModel: FavoritosViewModel,
    preferenciasViewModel: PreferenciasViewModel
) {
    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel
            )
        }

        composable("detalhes/{treinoId}") { backStackEntry ->
            val treinoId = backStackEntry.arguments?.getString("treinoId")?.toIntOrNull() ?: 0
            DetalhesScreen(
                treinoId = treinoId,
                navController = navController,
                favoritosViewModel = favoritosViewModel
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel
            )
        }

        composable("configuracoes") {
            ConfiguracoesScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                preferenciasViewModel = preferenciasViewModel
            )
        }

        composable("ajuda") {
            AjudaScreen(navController = navController)
        }

        composable("assistente") {
            AssistenteScreen() // ⚠️ Certifique-se de criar essa tela
        }
    }
}
