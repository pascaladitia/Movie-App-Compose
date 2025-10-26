package com.pascal.movie.ui.screen.detail.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import com.pascal.movie.data.local.entity.FavoritesEntity

val LocalDetailEvent = compositionLocalOf { DetailEvent() }

@Stable
data class DetailEvent(
    val onFavorite: (FavoritesEntity, Boolean) -> Unit = { _, _ -> },
    val onNavBack: () -> Unit = {},
)