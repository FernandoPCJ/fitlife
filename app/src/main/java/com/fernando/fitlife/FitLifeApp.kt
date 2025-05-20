package com.fernando.fitlife

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.fernando.fitlife.navigation.FitLifeNavGraph

@Composable
fun FitLifeApp() {
    val navController = rememberNavController()
    FitLifeNavGraph(navController = navController)
}
