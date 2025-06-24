package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.ui.components.BottomBar
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fernando.fitlife.ui.components.BotaoFavorito
import com.fernando.fitlife.ui.components.DetalheItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    navController: NavController,
    favoritosViewModel: FavoritosViewModel
) {
    val favoritos = favoritosViewModel.favoritos
    val isLoading by favoritosViewModel.isLoading.collectAsState()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "favoritos"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
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
                        .padding(16.dp)
                ) {
                    if (favoritos.isNotEmpty()) {
                        Button(
                            onClick = { favoritosViewModel.limparTodosComLoading() },
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Limpar todos os favoritos")
                        }
                    }

                    if (favoritos.isEmpty()) {
                        Text(
                            text = "Nenhum item favorito ainda.",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyColumn {
                            items(favoritos) { treino ->
                                TreinoFavoritoCard(
                                    treino = treino,
                                    isFavorito = favoritosViewModel.isFavorito(treino),
                                    onToggleFavorito = {
                                        favoritosViewModel.remover(treino)
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
        }
    }
}

@Composable
fun TreinoFavoritoCard(
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

                BotaoFavorito(
                    isFavorito = isFavorito,
                    onClick = onToggleFavorito
                )
            }

            DetalheItem(visible = mostrarDetalhes) {
                Text(
                    text = treino.descricao ?: "Sem descrição.",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
