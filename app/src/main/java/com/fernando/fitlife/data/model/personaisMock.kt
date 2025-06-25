package com.fernando.fitlife.data.model

import com.fernando.fitlife.R
import com.fernando.fitlife.model.Personal

val personaisMock = listOf(
    Personal(
        id = 1,
        nome = "homer simpson",
        especialidade = "Hipertrofia",
        descricao = "Treinador com foco em musculação e ganho de massa.",
        imagemUrl = R.drawable.personal1
    ),
    Personal(
        id = 2,
        nome = "johnny bravo",
        especialidade = "Emagrecimento",
        descricao = "Ajuda você a perder peso de forma saudável.",
         imagemUrl = R.drawable.personal2
    ),
    Personal(
        id = 3,
        nome = "pai do chris",
        especialidade = "Corrida",
        descricao = "Especialista em performance e resistência.",
         imagemUrl = R.drawable.personal3
    )
)
