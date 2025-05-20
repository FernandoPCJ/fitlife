package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.fernando.fitlife.data.model.treinosMock

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DetalhesScreen(treinoId: Int) {
    val treino = treinosMock.find { it.id == treinoId } ?: return // Proteção contra ID inválido

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(treino.nome) }
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

            Text(
                text = treino.descricao,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text("Duração: ${treino.duracaoMin} minutos")
            Text("Nível: ${treino.nivel}")

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /* ação futura */ }) {
                Text("Adicionar aos Favoritos")
            }
        }
    }
}
