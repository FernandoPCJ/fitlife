package com.fernando.fitlife.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fernando.fitlife.model.Treino
import kotlinx.coroutines.flow.Flow

@Dao
interface TreinoDao {
    @Query("SELECT * FROM treinos WHERE userId = :userId")
    fun getFavoritos(userId: String): Flow<List<Treino>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adicionar(treino: Treino)

    @Delete
    suspend fun remover(treino: Treino)

    @Query("DELETE FROM treinos WHERE userId = :userId")
    suspend fun clear(userId: String)
}