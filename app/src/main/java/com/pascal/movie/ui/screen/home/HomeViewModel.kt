package com.pascal.movie.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import com.pascal.movie.domain.model.movie.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState get() = _uiState.asStateFlow()

    private val _movies = MutableStateFlow(PagingData.empty<Movies>())
    val movies: StateFlow<PagingData<Movies>> = _movies

    suspend fun loadMovies(selection: Int) {
        _uiState.update {
            it.copy(isLoading = true)
        }

        repository.getMovies(selection)
            .cachedIn(viewModelScope)
            .collect {
                _movies.value = it
            }
    }

    suspend fun loadMovies2() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        try {
            val result = repository.getMoviesCatalog(1)

            if (result.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movies2 = result
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                    message = e.message.toString()
                )
            }
        }
    }

    fun setLoading(boolean: Boolean) {
        _uiState.update { it.copy(isLoading = boolean) }
    }

    fun setError(boolean: Boolean) {
        _uiState.update { it.copy(isError = boolean) }
    }
}

