package com.pascal.movie.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import com.pascal.movie.domain.base.UiState
import com.pascal.movie.domain.model.mapping.MovieDetailMapping
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {

    private val _movieDetailUiState = MutableStateFlow<UiState<MovieDetailMapping?>>(UiState.Loading)
    val movieDetailUiState: StateFlow<UiState<MovieDetailMapping?>> = _movieDetailUiState

    suspend fun loadDetailMovie(id: Int) {
        try {
            val movie = repository.getSingleMovie(id)
            val videos = repository.getMovieVideos(id)
            val reviews = repository.getMovieReviews(id)
            val favorite = database.getFavoriteMovie(id)

            val mapping = MovieDetailMapping(
                reviews.results,
                videos.results,
                movie,
                favorite
            )

            _movieDetailUiState.value = UiState.Success(mapping)
        } catch (e: Exception) {
            _movieDetailUiState.value = UiState.Error(e.message.toString())
        }
    }

    suspend fun updateFavMovie(item: FavoritesEntity, checkFav: Boolean) {
        if (checkFav) {
            database.insertFavoriteItem(item)
        } else {
            database.deleteFavoriteItem(item)
        }
    }
}