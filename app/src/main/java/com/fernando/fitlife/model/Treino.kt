package com.fernando.fitlife.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "treinos")
data class Treino(
    @PrimaryKey
    val id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val imagemUrl: String = "",
    val duracaoMin: Int = 0,
    val nivel: String = "",
    val trainerId: String = "",
    val clientId: String = "",
    val userId: String = ""
)