package com.pascal.movie.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import com.pascal.movie.domain.base.UiState
import com.pascal.movie.domain.model.mapping.MovieDetailMapping
import com.pascal.movie.domain.model.movie.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {

    private val _movies = MutableStateFlow(PagingData.empty<Movies>())
    val movies: StateFlow<PagingData<Movies>> = _movies

    private val _movieDetailUiState = MutableStateFlow<UiState<MovieDetailMapping?>>(UiState.Loading)
    val movieDetailUiState: StateFlow<UiState<MovieDetailMapping?>> = _movieDetailUiState


    suspend fun loadMovies(selection: Int) {
        repository.getMovies(selection)
            .cachedIn(viewModelScope)
            .collect {
                _movies.value = it
            }
    }

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