package com.fernando.fitlife.repository

import com.fernando.fitlife.model.Treino
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getWorkoutsForUser(userId: String): List<Treino> {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("treinos")
            .get()
            .await()
        return snapshot.toObjects(Treino::class.java)
    }
}
