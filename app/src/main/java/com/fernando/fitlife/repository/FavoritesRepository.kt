package com.fernando.fitlife.repository

import android.content.Context
import com.fernando.fitlife.database.FitLifeDatabase
import com.fernando.fitlife.model.Treino
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(context: Context) {
    private val dao = FitLifeDatabase.getDatabase(context).treinoDao()

    fun getFavorites(userId: String): Flow<List<Treino>> = dao.getFavoritos(userId)

    suspend fun addFavorite(treino: Treino, userId: String) {
        dao.adicionar(treino.copy(userId = userId))
    }

    suspend fun removeFavorite(treino: Treino) {
        dao.remover(treino)
    }

    suspend fun clear(userId: String) {
        dao.clear(userId)
    }
}
