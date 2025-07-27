package com.fernando.fitlife.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.model.TrainerWorkout
import com.fernando.fitlife.viewmodel.AuthViewModel
import com.fernando.fitlife.viewmodel.TrainerViewModel

@Composable
fun TrainerMenuScreen(
    navController: NavController,
    trainerViewModel: TrainerViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var duracao by remember { mutableStateOf("") }
    var nivel by remember { mutableStateOf("") }
    var selectedClient by remember { mutableStateOf<com.fernando.fitlife.model.Client?>(null) }
    var uploadTarget by remember { mutableStateOf<Pair<String, String>?>(null) }
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        val target = uploadTarget
        if (uri != null && target != null) {
            trainerViewModel.uploadImage(target.first, target.second, uri, authViewModel.currentUser!!.uid)
        }
    }

    LaunchedEffect(Unit) {
        trainerViewModel.loadClients()
        authViewModel.currentUser?.uid?.let { trainerViewModel.loadTrainerWorkouts(it) }
    }

    LaunchedEffect(selectedClient) {
        selectedClient?.let { trainerViewModel.loadWorkouts(it.id) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome do treino") })
        OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") })
        OutlinedTextField(value = duracao, onValueChange = { duracao = it }, label = { Text("Duração em minutos") })
        OutlinedTextField(value = nivel, onValueChange = { nivel = it }, label = { Text("Nível") })

        Text("Selecione o cliente:")
        LazyColumn(modifier = Modifier.height(150.dp)) {
            items(trainerViewModel.clients) { client ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(client.nome)
                    RadioButton(selected = selectedClient == client, onClick = { selectedClient = client })
                }
            }
        }

        Button(
            onClick = {
                val client = selectedClient
                if (client != null) {
                    val treino = Treino(
                        id = "${System.currentTimeMillis()}",
                        nome = nome,
                        descricao = descricao,
                        duracaoMin = duracao.toIntOrNull() ?: 0,
                        nivel = nivel,
                        trainerId = authViewModel.currentUser!!.uid,
                        clientId = client.id
                    )
                    trainerViewModel.addWorkout(client.id, treino)
                    nome = ""
                    descricao = ""
                    duracao = ""
                    nivel = ""
                    selectedClient = null
                }
            },
            enabled = selectedClient != null && nome.isNotBlank() && duracao.isNotBlank()
        ) {
            Text("Criar treino")
        }

        if (trainerViewModel.trainerWorkouts.isNotEmpty()) {
            Text("Seus treinos:")
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(trainerViewModel.trainerWorkouts) { item ->
                    val workoutId = item.id
                    val treino = item.treino
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(treino.nome)
                            Text("Aluno: ${item.clientName}")
                        }
                        Row {
                            TextButton(onClick = {
                                uploadTarget = treino.clientId to workoutId
                                imageLauncher.launch("image/*")
                            }) { Text("Foto") }
                            TextButton(onClick = { trainerViewModel.deleteWorkout(treino.clientId, workoutId, authViewModel.currentUser!!.uid) }) { Text("Excluir") }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (selectedClient != null) {
            Text("Treinos do cliente:")
            LazyColumn(
                modifier = Modifier.height(200.dp)
            ) {
                items(trainerViewModel.clientWorkouts) { treino ->
                    Text("- ${treino.nome}")
                }
            }
        }
    }
}
