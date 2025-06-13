package com.fernando.fitlife.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fernando.fitlife.ui.screens.*
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.PreferenciasViewModel

object Routes {
    const val HOME = "home"
    const val DETALHES = "detalhes/{treinoId}"
    const val FAVORITOS = "favoritos"
    const val CONFIGURACOES = "configuracoes"
    const val AJUDA = "ajuda"
}

@Composable
fun FitLifeNavGraph(
    navController: NavHostController,
    favoritosViewModel: FavoritosViewModel,
    preferenciasViewModel: PreferenciasViewModel
) {
    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel
            )
        }

        composable(Routes.DETALHES) { backStackEntry ->
            val treinoId = backStackEntry.arguments?.getString("treinoId")?.toIntOrNull()
            if (treinoId != null) {
                DetalhesScreen(
                    treinoId = treinoId,
                    navController = navController,
                    favoritosViewModel = favoritosViewModel
                )
            } else {
                navController.popBackStack()
            }
        }

        composable(Routes.FAVORITOS) {
            FavoritosScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel
            )
        }

        composable(Routes.CONFIGURACOES) {
            ConfiguracoesScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                preferenciasViewModel = preferenciasViewModel
            )
        }

        composable(Routes.AJUDA) {
            AjudaScreen(navController = navController)
        }
    }
}