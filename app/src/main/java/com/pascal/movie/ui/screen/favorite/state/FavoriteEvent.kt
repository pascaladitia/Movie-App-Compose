package com.pascal.movie.ui.screen.favorite.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import com.pascal.movie.domain.model.Movie

val LocalFavoriteEvent = compositionLocalOf { FavoriteEvent() }

@Stable
data class FavoriteEvent(
    val onCategory: () -> Unit = {},
    val onDetail: (Movie) -> Unit ={}
)