package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.fernando.fitlife.data.model.treinosMock
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.ui.components.BottomBar
import com.fernando.fitlife.ui.components.BotaoFavorito
import com.fernando.fitlife.ui.components.DetalheItem
import com.fernando.fitlife.viewmodel.FavoritosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    favoritosViewModel: FavoritosViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "home"

    var busca by remember { mutableStateOf("") }
    val treinosFiltrados = treinosMock.filter {
        it.nome.contains(busca, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FitLife") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = {
                                expanded = false
                                navController.navigate("favoritos")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Configurações") },
                            onClick = {
                                expanded = false
                                navController.navigate("configuracoes")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Ajuda") },
                            onClick = {
                                expanded = false
                                navController.navigate("ajuda")
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController, currentRoute = currentRoute)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = busca,
                onValueChange = { busca = it },
                label = { Text("Buscar treino...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn {
                items(treinosFiltrados) { treino ->
                    TreinoCard(
                        treino = treino,
                        isFavorito = favoritosViewModel.isFavorito(treino),
                        onToggleFavorito = {
                            if (favoritosViewModel.isFavorito(treino)) {
                                favoritosViewModel.remover(treino)
                            } else {
                                favoritosViewModel.adicionar(treino)
                            }
                        },
                        onClick = {
                            navController.navigate("detalhes/${treino.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TreinoCard(
    treino: Treino,
    isFavorito: Boolean,
    onToggleFavorito: () -> Unit,
    onClick: () -> Unit
) {
    var mostrarDetalhes by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                mostrarDetalhes = !mostrarDetalhes
                onClick()
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = treino.imagemUrl,
                    contentDescription = treino.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(treino.nome, fontWeight = FontWeight.Bold)
                    Text("${treino.duracaoMin} min • ${treino.nivel}")
                }

                // Animação de favorito
                BotaoFavorito(
                    isFavorito = isFavorito,
                    onClick = onToggleFavorito
                )
            }

            // Animação de exibição de detalhes
            DetalheItem(visible = mostrarDetalhes) {
                Text(
                    text = treino.descricao ?: "Sem descrição.",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
