package com.pascal.movie.ui.screen.favorite.state

import com.pascal.movie.domain.model.Movie
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FavoriteUIState(
    val isLoading: Boolean = false,
    val isError: Pair<Boolean, String> = false to "",
    val moviesResponse: ImmutableList<Movie> = persistentListOf()
)