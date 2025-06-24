package com.fernando.fitlife.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun BotaoFavorito(
    isFavorito: Boolean,
    onClick: () -> Unit
) {
    val corAnimada by animateColorAsState(
        targetValue = if (isFavorito) Color.Red else Color.Gray,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = corAnimada
        )
    }
}
