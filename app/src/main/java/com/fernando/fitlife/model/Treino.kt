package com.fernando.fitlife.model

data class Treino(
    val id: Int,
    val nome: String,
    val descricao: String,
    val imagemUrl: Int,
    val duracaoMin: Int,
    val nivel: String
)
