package com.fernando.fitlife.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.fitlife.model.Treino
import com.fernando.fitlife.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutsViewModel : ViewModel() {
    private val repo = WorkoutRepository()

    var workouts by mutableStateOf<List<Treino>>(emptyList())
        private set

    fun loadWorkouts(userId: String) {
        viewModelScope.launch {
            workouts = repo.getWorkoutsForUser(userId)
        }
    }
}
