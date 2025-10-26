package com.pascal.movie.ui.screen.home.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.ui.screen.home.MovieTab

val LocalHomeEvent = compositionLocalOf { HomeEvent() }

@Stable
data class HomeEvent(
    val onCategory: (MovieTab) -> Unit = {},
    val onDetail: (Movie) -> Unit ={}
)