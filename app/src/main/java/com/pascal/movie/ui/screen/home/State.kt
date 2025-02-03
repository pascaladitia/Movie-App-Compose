package com.pascal.movie.ui.screen.home

import com.pascal.movie.domain.model.movie.Movies

data class HomeUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val movies2: List<Movies> = emptyList()
)

data class HomeUIEvent(
    val onCategory: (Int) -> Unit = {},
    val onDetail: (Movies) -> Unit ={}
)