package com.fernando.fitlife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fernando.fitlife.ui.screens.ConfiguracoesScreen
import com.fernando.fitlife.ui.screens.ListaScreen
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.ListaViewModel
import com.fernando.fitlife.viewmodel.PreferenciasViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    favoritosViewModel: FavoritosViewModel,
    preferenciasViewModel: PreferenciasViewModel,
    listaViewModel: ListaViewModel
) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            ListaScreen(listaViewModel)
        }
        composable("configuracoes") {
            ConfiguracoesScreen(
                navController = navController,
                favoritosViewModel = favoritosViewModel,
                preferenciasViewModel = preferenciasViewModel
            )
        }
        // Adicione outras telas conforme necessário
    }
}
