package com.pascal.movie.ui.screen.home

import android.util.Log
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

    private val _movies2 = MutableStateFlow<UiState<List<Movies>>>(UiState.Empty)
    val movies2: StateFlow<UiState<List<Movies>>> = _movies2

    suspend fun loadMovies(selection: Int) {
        repository.getMovies(selection)
            .cachedIn(viewModelScope)
            .collect {
                _movies.value = it
            }
    }

    suspend fun loadMovies2() {
        try {
            val result = repository.getMoviesCatalog(1)

            if (result.isEmpty()) {
                _movies2.value = UiState.Empty
            } else {
                _movies2.value = UiState.Success(result)
            }
        } catch (e: Exception) {
            _movies2.value = UiState.Error(e.localizedMessage ?: e.message.toString())
        }
    }

}