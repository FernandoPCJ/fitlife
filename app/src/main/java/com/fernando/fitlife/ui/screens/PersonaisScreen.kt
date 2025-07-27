package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.fernando.fitlife.model.Personal
import com.fernando.fitlife.viewmodel.FavoritosPersonalViewModel
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.LocalContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaisScreen(
    navController: NavController,
    favoritosViewModel: FavoritosPersonalViewModel,
    trainerViewModel: com.fernando.fitlife.viewmodel.TrainerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val favoritos by favoritosViewModel.favoritos.collectAsState()
    val trainers = trainerViewModel.trainers

    LaunchedEffect(Unit) {
        trainerViewModel.loadTrainers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personais") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(trainers) { personal ->
                PersonalCard(
                    personal = personal,
                    favoritosViewModel = favoritosViewModel
                )
            }
        }
    }
}

@Composable
fun PersonalCard(
    personal: Personal,
    favoritosViewModel: FavoritosPersonalViewModel
) {
    val favoritos by favoritosViewModel.favoritos.collectAsState()
    val isFavorito = favoritos.contains(personal)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(personal.imagemUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(personal.nome, style = MaterialTheme.typography.titleMedium)
                Text(personal.especialidade, style = MaterialTheme.typography.bodyMedium)
                Text(personal.descricao, style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = { favoritosViewModel.adicionarOuRemover(personal) }) {
                Icon(
                    imageVector = if (isFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = if (isFavorito) MaterialTheme.colorScheme.error else LocalContentColor.current
                )
            }
        }
    }
}
