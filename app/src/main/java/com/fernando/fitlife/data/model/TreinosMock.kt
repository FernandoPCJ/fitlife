package com.fernando.fitlife.data.model

import com.fernando.fitlife.model.Treino

val treinosMock = listOf(
    Treino(
        id = 1,
        nome = "Treino Cardio",
        descricao = "Queime calorias e melhore seu condicionamento.",
        imagemUrl = "https://cdn.pixabay.com/photo/2016/11/19/14/00/athletes-1835371_1280.jpg",
        duracaoMin = 30,
        nivel = "Iniciante"
    ),
    Treino(
        id = 2,
        nome = "Musculação Avançada",
        descricao = "Foco em hipertrofia e ganho de massa.",
        imagemUrl = "https://cdn.pixabay.com/photo/2014/12/03/19/00/bodybuilder-557773_1280.jpg",
        duracaoMin = 60,
        nivel = "Avançado"
    ),
    Treino(
        id = 3,
        nome = "Yoga Relaxante",
        descricao = "Sessão para aliviar o estresse e melhorar a flexibilidade.",
        imagemUrl = "https://cdn.pixabay.com/photo/2020/01/06/07/46/yoga-4748578_1280.jpg",
        duracaoMin = 45,
        nivel = "Intermediário"
    ),
    Treino(
        id = 4,
        nome = "HIIT Explosivo",
        descricao = "Treino intenso em intervalos para queima rápida de gordura.",
        imagemUrl = "https://cdn.pixabay.com/photo/2016/11/14/03/06/people-1822459_1280.jpg",
        duracaoMin = 20,
        nivel = "Avançado"
    )
)
