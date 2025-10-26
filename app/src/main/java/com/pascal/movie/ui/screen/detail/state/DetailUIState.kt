package com.pascal.movie.ui.screen.detail.state

import com.pascal.movie.domain.model.MovieDetailMapping

data class DetailUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val movies: MovieDetailMapping? = null
)
