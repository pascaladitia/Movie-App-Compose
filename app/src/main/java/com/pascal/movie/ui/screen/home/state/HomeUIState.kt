package com.pascal.movie.ui.screen.home.state

import com.pascal.movie.domain.model.Movie

data class HomeUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val moviesResponse2: List<Movie> = emptyList()
)