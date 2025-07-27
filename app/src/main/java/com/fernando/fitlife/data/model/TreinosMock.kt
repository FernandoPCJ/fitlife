package com.fernando.fitlife.data.model

import com.fernando.fitlife.model.Treino

val treinosMock = listOf(
    Treino(
        id = "1",
        nome = "Treino Cardio",
        descricao = "Queime calorias e melhore seu condicionamento.",
        imagemUrl = "",
        duracaoMin = 30,
        nivel = "Iniciante"
    ),
    Treino(
        id = "2",
        nome = "Musculação Avançada",
        descricao = "Foco em hipertrofia e ganho de massa.",
        imagemUrl = "",
        duracaoMin = 60,
        nivel = "Avançado"
    ),
    Treino(
        id = "3",
        nome = "Yoga Relaxante",
        descricao = "Sessão para aliviar o estresse e melhorar a flexibilidade.",
        imagemUrl = "",
        duracaoMin = 45,
        nivel = "Intermediário"
    ),
    Treino(
        id = "4",
        nome = "HIIT Explosivo",
        descricao = "Treino intenso em intervalos para queima rápida de gordura.",
        imagemUrl = "",
        duracaoMin = 20,
        nivel = "Avançado"
    )
)
