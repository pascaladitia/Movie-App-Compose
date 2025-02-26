package com.pascal.movie.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import com.pascal.movie.domain.model.mapping.MovieDetailMapping
import com.pascal.movie.domain.model.movie.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUIState())
    val uiState get() = _uiState.asStateFlow()

    suspend fun loadDetailMovie(movie: Movies?) {
        _uiState.update { it.copy(isLoading = true) }

        try {
            val videos = repository.getMovieVideos(movie?.id ?: 0)
            val favorite = database.getFavoriteMovie(movie?.id ?: 0)
//            val reviews = repository.getMovieReviews(movie?.id ?: 0)

            val mapping = MovieDetailMapping(
                null,
                videos.results,
                movie,
                favorite
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    movies = mapping
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                    message = e.message.toString(),
                )
            }
        }
    }

    suspend fun updateFavMovie(item: FavoritesEntity, checkFav: Boolean) {
        if (checkFav) {
            database.insertFavoriteItem(item)
        } else {
            database.deleteFavoriteItem(item)
        }
    }

    fun setLoading(boolean: Boolean) {
        _uiState.update { it.copy(isLoading = boolean) }
    }

    fun setError(boolean: Boolean) {
        _uiState.update { it.copy(isError = boolean) }
    }
}