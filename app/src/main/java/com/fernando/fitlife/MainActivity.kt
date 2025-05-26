package com.fernando.fitlife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.fernando.fitlife.viewmodel.FavoritosViewModel
import com.fernando.fitlife.viewmodel.PreferenciasViewModel

class MainActivity : ComponentActivity() {

    // Boa prática: usar 'by viewModels()' para garantir o ciclo de vida adequado
    private val favoritosViewModel: FavoritosViewModel by viewModels()
    private val preferenciasViewModel: PreferenciasViewModel by viewModels()

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
