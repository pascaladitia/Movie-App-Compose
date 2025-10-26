package com.pascal.movie.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.domain.model.MovieDetailMapping
import com.pascal.movie.domain.usecase.movie.MovieUseCase
import com.pascal.movie.ui.screen.detail.state.DetailUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieUseCase: MovieUseCase,
    private val localUseCase: LocalRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUIState())
    val uiState: StateFlow<DetailUIState> = _uiState.asStateFlow()

    fun loadDetailMovie(movie: Movie?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val movieId = movie?.id ?: 0

                combine(
                    movieUseCase.getMovieVideos(movieId),
                    movieUseCase.getMovieReviews(movieId)
                ) { videos, reviews ->
                    val favorite = localUseCase.getFavoriteMovie(movieId)
                    MovieDetailMapping(
                        review = reviews.results,
                        videos = videos.results,
                        movie = movie,
                        favorite = favorite
                    )
                }.collectLatest { mapping ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            movies = mapping
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = e.message.orEmpty()
                    )
                }
            }
        }
    }

    fun updateFavMovie(item: FavoritesEntity, checkFav: Boolean) {
        viewModelScope.launch {
            if (checkFav) {
                localUseCase.insertFavoriteItem(item)
            } else {
                localUseCase.deleteFavoriteItem(item)
            }
        }
    }

    fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    fun setError(value: Boolean) {
        _uiState.update { it.copy(isError = value) }
    }
}
