package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Add
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.ui.components.BottomBar
import com.fernando.fitlife.ui.components.BotaoFavorito
import com.fernando.fitlife.ui.components.DetalheItem
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.AuthViewModel
import com.fernando.fitlife.viewmodel.WorkoutsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    favoritosViewModel: FavoritosViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    workoutsViewModel: WorkoutsViewModel = viewModel(),
    trainerViewModel: com.fernando.fitlife.viewmodel.TrainerViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var uploadTarget by remember { mutableStateOf<Pair<String, String>?>(null) }
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        val target = uploadTarget
        if (uri != null && target != null) {
            trainerViewModel.uploadImage(target.first, target.second, uri, authViewModel.currentUser!!.uid)
        }
    }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "home"

    val role by authViewModel.role.collectAsState()

    LaunchedEffect(Unit) {
        if (authViewModel.currentUser != null) {
            authViewModel.loadRole()
            workoutsViewModel.loadWorkouts(authViewModel.currentUser!!.uid)
            trainerViewModel.loadTrainerWorkouts(authViewModel.currentUser!!.uid)
            favoritosViewModel.setUser(authViewModel.currentUser!!.uid)
        }
    }

    var busca by remember { mutableStateOf("") }
    val trainerTreinos = trainerViewModel.trainerWorkouts.filter {
        it.treino.nome.contains(busca, ignoreCase = true)
    }
    val clientTreinos = workoutsViewModel.workouts.filter {
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
                        DropdownMenuItem(
                            text = { Text("Sair") },
                            onClick = {
                                expanded = false
                                favoritosViewModel.clearInMemory()
                                authViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
            if (role == "trainer") {
                FloatingActionButton(onClick = { navController.navigate("trainer") }) {
                    Icon(Icons.Default.Add, contentDescription = "Criar treino")
                }
            }
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
                if (role == "trainer") {
                    items(trainerTreinos) { item ->
                        TrainerWorkoutCard(
                            workout = item,
                            onUploadPhoto = {
                                uploadTarget = item.treino.clientId to item.id
                                imageLauncher.launch("image/*")
                            },
                            onDelete = {
                                trainerViewModel.deleteWorkout(item.treino.clientId, item.id, authViewModel.currentUser!!.uid)
                            },
                            onClick = {
                                navController.navigate("detalhes/${item.treino.id}")
                            }
                        )
                    }
                } else {
                    items(clientTreinos) { treino ->
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

@Composable
fun TrainerWorkoutCard(
    workout: com.fernando.fitlife.model.TrainerWorkout,
    onUploadPhoto: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val treino = workout.treino
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
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
                    Text("Aluno: ${workout.clientName}")
                    Text("${treino.duracaoMin} min • ${treino.nivel}")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onUploadPhoto) { Text("Foto") }
                TextButton(onClick = onDelete) { Text("Excluir") }
            }
        }
    }
}
