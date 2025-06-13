package com.fernando.fitlife.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    tint: Color = Color.Red
) {
    Icon(
        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = if (isFavorite) "Remover dos favoritos" else "Adicionar aos favoritos",
        tint = tint,
        modifier = modifier
    )
}