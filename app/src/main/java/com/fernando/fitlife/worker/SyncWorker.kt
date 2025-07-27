package com.fernando.fitlife.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fernando.fitlife.database.FitLifeDatabase
import com.fernando.fitlife.repository.FirebaseRepository
import kotlinx.coroutines.flow.first

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val database = FitLifeDatabase.getDatabase(applicationContext)
        val firebaseRepository = FirebaseRepository()

        val userId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.success()
        val favoritos = database.treinoDao().getFavoritos(userId).first()
        firebaseRepository.syncFavoritos(userId, favoritos)

        return Result.success()
    }
}