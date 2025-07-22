package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AssistenteScreen() {
    var pergunta by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Assistente", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pergunta,
            onValueChange = { pergunta = it },
            label = { Text("Digite sua pergunta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            // chamada ao viewmodel (adicionar depois)
        }) {
            Text("Perguntar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Resposta aparecerá aqui")
    }
}
