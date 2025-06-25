package com.fernando.fitlife.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class BottomNavItem(val label: String, val route: String, val icon: ImageVector)

val bottomNavItems = listOf(
    BottomNavItem("Início", "home", Icons.Default.Home),
    BottomNavItem("Favoritos", "favoritos", Icons.Default.Favorite),
    BottomNavItem("Personal", "personais", Icons.Default.SmartToy) // substituído
)

@Composable
fun BottomBar(navController: NavController, currentRoute: String) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
