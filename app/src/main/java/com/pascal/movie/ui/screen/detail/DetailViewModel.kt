package com.pascal.movie.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.domain.model.MovieDetailMapping
import com.pascal.movie.domain.usecase.local.LocalUseCase
import com.pascal.movie.domain.usecase.movie.MovieUseCase
import com.pascal.movie.ui.screen.detail.state.DetailUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieUseCase: MovieUseCase,
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUIState())
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()

    suspend fun loadDetailMovie(movie: Movie?) {
        _uiState.update { it.copy(isLoading = true) }

        val movieId = movie?.id ?: 0

        combine(
            movieUseCase.getMovieVideos(movieId),
            movieUseCase.getMovieReviews(movieId)
        ) { videos, reviews ->
            val favorite = localUseCase.getFavorite(movieId)
            MovieDetailMapping(
                review = reviews.results,
                videos = videos.results,
                movie = movie,
                favorite = favorite
            )
        }.catch {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                    message = it.message
                )
            }
        }.collectLatest { mapping ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    movies = mapping
                )
            }
        }
    }

    fun updateFavMovie(item: Movie?, checkFav: Boolean) {
        if (item == null) return

        viewModelScope.launch {
            if (checkFav) {
                localUseCase.insertFavorite(item)
            } else {
                localUseCase.deleteFavorite(item)
            }
        }
    }

    fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    fun setError(value: Boolean) {
        _uiState.update { it.copy(isError = value) }
    }

    override fun onCleared() {
        super.onCleared()

        _uiState.value = DetailUIState()
    }
}
