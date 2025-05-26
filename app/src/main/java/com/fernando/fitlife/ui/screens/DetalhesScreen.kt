package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fernando.fitlife.data.model.treinosMock
import com.fernando.fitlife.viewmodel.FavoritosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesScreen(
    treinoId: Int,
    navController: NavController,
    favoritosViewModel: FavoritosViewModel = viewModel()
) {
    val treino = treinosMock.find { it.id == treinoId } ?: return
    val isFavorito by remember { derivedStateOf { favoritosViewModel.isFavorito(treino) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(treino.nome) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = treino.imagemUrl,
                contentDescription = treino.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(treino.descricao, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(12.dp))
            Text("Duração: ${treino.duracaoMin} minutos")
            Text("Nível: ${treino.nivel}")

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                if (isFavorito) {
                    favoritosViewModel.remover(treino)
                } else {
                    favoritosViewModel.adicionar(treino)
                }
            }) {
                Text(if (isFavorito) "Remover dos Favoritos" else "Adicionar aos Favoritos")
            }
        }
    }
}
