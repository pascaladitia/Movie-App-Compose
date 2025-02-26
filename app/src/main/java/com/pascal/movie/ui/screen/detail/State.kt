package com.pascal.movie.ui.screen.detail

import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.domain.model.mapping.MovieDetailMapping

data class DetailUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val movies: MovieDetailMapping? = null
)

data class DetailUIEvent(
    val onFavorite: (FavoritesEntity, Boolean) -> Unit = { _, _ -> },
    val onNavBack: () -> Unit = {},
)