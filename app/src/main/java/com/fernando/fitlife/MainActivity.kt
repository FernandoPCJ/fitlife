package com.fernando.fitlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.PreferenciasViewModel
import com.fernando.fitlife.repository.PreferenciasRepository

class MainActivity : ComponentActivity() {

    
    private val preferenciasViewModel: PreferenciasViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = PreferenciasRepository(applicationContext)
                return PreferenciasViewModel(repo) as T
            }
        }
    }

    private val favoritosViewModel: FavoritosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitLifeApp(
                favoritosViewModel = favoritosViewModel,
                preferenciasViewModel = preferenciasViewModel
            )
        }
    }
}