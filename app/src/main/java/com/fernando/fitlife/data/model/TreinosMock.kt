package com.fernando.fitlife.data.model

import com.fernando.fitlife.R
import com.fernando.fitlife.model.Treino

val treinosMock = listOf(
    Treino(
        id = 1,
        nome = "Treino Cardio",
        descricao = "Queime calorias e melhore seu condicionamento.",
        imagemUrl = R.drawable.treino1,
        duracaoMin = 30,
        nivel = "Iniciante"
    ),
    Treino(
        id = 2,
        nome = "Musculação Avançada",
        descricao = "Foco em hipertrofia e ganho de massa.",
        imagemUrl = R.drawable.treino2,
        duracaoMin = 60,
        nivel = "Avançado"
    ),
    Treino(
        id = 3,
        nome = "Yoga Relaxante",
        descricao = "Sessão para aliviar o estresse e melhorar a flexibilidade.",
        imagemUrl = R.drawable.treino3,
        duracaoMin = 45,
        nivel = "Intermediário"
    ),
    Treino(
        id = 4,
        nome = "HIIT Explosivo",
        descricao = "Treino intenso em intervalos para queima rápida de gordura.",
        imagemUrl = R.drawable.treino4,
        duracaoMin = 20,
        nivel = "Avançado"
    )
)
