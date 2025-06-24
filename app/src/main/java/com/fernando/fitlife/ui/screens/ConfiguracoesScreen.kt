package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fernando.fitlife.ui.components.BottomBar
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracoesScreen(
    navController: NavController,
    favoritosViewModel: FavoritosViewModel,
    settingsViewModel: SettingsViewModel
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "configuracoes"

    val darkTheme by settingsViewModel.darkMode.collectAsState()
    val notificacoes by settingsViewModel.notifications.collectAsState()
    val isLoading by favoritosViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Configurações") })
        },
        bottomBar = {
            BottomBar(navController = navController, currentRoute = currentRoute)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text("Preferências", style = MaterialTheme.typography.titleMedium)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Modo Escuro")
                        Switch(
                            checked = darkTheme,
                            onCheckedChange = { settingsViewModel.setDarkMode(!darkTheme) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Notificações")
                        Switch(
                            checked = notificacoes,
                            onCheckedChange = { settingsViewModel.setNotifications(!notificacoes) }
                        )
                    }

                    Divider()

                    Text("Ações", style = MaterialTheme.typography.titleMedium)

                    Button(
                        onClick = { favoritosViewModel.limparTodosComLoading() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Limpar Favoritos")
                    }
                }
            }
        }
    }
}
