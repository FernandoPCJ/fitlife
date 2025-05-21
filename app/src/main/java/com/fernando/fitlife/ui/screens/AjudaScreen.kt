package com.fernando.fitlife.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fernando.fitlife.ui.components.BottomBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjudaScreen(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "ajuda"

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val faqList = listOf(
        "Como personalizar um treino?" to "Na tela inicial, toque em um treino e veja os detalhes. Você poderá ajustar conforme sua necessidade.",
        "Como adicionar aos favoritos?" to "Clique no ícone de coração no card ou na tela de detalhes do treino.",
        "Como redefinir preferências?" to "Acesse a aba de Configurações e clique em 'Redefinir Preferências'."
    )

    // Controla quais perguntas estão expandidas
    val expandedIndexes = remember { mutableStateMapOf<Int, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajuda e Suporte") }
            )
        },
        bottomBar = {
            BottomBar(navController = navController, currentRoute = currentRoute)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Perguntas Frequentes", style = MaterialTheme.typography.titleMedium)

            faqList.forEachIndexed { index, (pergunta, resposta) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandedIndexes[index] = !(expandedIndexes[index] ?: false)
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(pergunta, style = MaterialTheme.typography.bodyLarge)
                        if (expandedIndexes[index] == true) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(resposta, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Mensagem enviada para o suporte!")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar em Contato com o Suporte")
            }
        }
    }
}
