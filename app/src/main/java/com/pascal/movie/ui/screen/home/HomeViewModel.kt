package com.pascal.movie.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.domain.usecase.local.LocalUseCase
import com.pascal.movie.domain.usecase.movie.MovieUseCase
import com.pascal.movie.ui.screen.home.state.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val movieUseCase: MovieUseCase,
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    private val _moviesResponse = MutableStateFlow(PagingData.empty<Movie>())
    val moviesResponse: StateFlow<PagingData<Movie>> = _moviesResponse

    fun loadMovies(selection: MovieTab) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            movieUseCase.getMovies(selection)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesResponse.value = pagingData
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    fun loadMoviesCatalog(page: Int = 1) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            movieUseCase.getMoviesCatalog(page)
                .collectLatest { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            moviesResponse2 = result
                        )
                    }
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

enum class MovieTab(val title: String) {
    TRENDING("Trending"),
    TOP_RATED("Top Rated"),
    NOW_PLAYING("Now Playing"),
    UPCOMING("Upcoming"),
    TV_SHOWS("TV Shows")
}
