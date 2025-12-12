package com.pascal.movie.ui.screen.detail.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import com.pascal.movie.domain.model.Movie

val LocalDetailEvent = compositionLocalOf { DetailEvent() }

@Stable
data class DetailEvent(
    val onFavorite: (Movie?, Boolean) -> Unit = { _, _ -> },
    val onNavBack: () -> Unit = {},
)