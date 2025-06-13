package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fernando.fitlife.viewmodel.ListaViewModel

@Composable
fun ListaScreen(listaViewModel: ListaViewModel) {
    val isLoading by listaViewModel.isLoading.collectAsState()
    val itens by listaViewModel.itens.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                itens.forEach { item ->
                    Text(item.toString(), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}