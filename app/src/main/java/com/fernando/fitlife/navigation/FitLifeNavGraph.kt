package com.fernando.fitlife.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fernando.fitlife.ui.screens.DetalhesScreen
import com.fernando.fitlife.ui.screens.HomeScreen

@Composable
fun FitLifeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("detalhes/{treinoId}") { backStackEntry ->
            val treinoId = backStackEntry.arguments?.getString("treinoId")?.toIntOrNull() ?: 0
            DetalhesScreen(treinoId)
        }
    }
}