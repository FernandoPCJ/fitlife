package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.ui.components.BottomBar
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    navController: NavController,
    favoritosViewModel: FavoritosViewModel
) {
    val favoritos = favoritosViewModel.favoritos
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
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (favoritos.isNotEmpty()) {
                Button(
                    onClick = { favoritosViewModel.limparTodos() },
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
                        TreinoFavoritoCard(treino = treino) {
                            navController.navigate("detalhes/${treino.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TreinoFavoritoCard(treino: Treino, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = treino.imagemUrl,
                contentDescription = treino.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(treino.nome, fontWeight = FontWeight.Bold)
                Text("${treino.duracaoMin} min • ${treino.nivel}")
            }
        }
    }
}
