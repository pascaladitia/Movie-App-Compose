package com.pascal.movie.ui.screen.favorite

import androidx.lifecycle.ViewModel
import com.pascal.movie.domain.usecase.local.LocalUseCase
import com.pascal.movie.domain.usecase.movie.MovieUseCase
import com.pascal.movie.ui.screen.favorite.state.FavoriteUIState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update

class FavoriteViewModel(
    private val movieUseCase: MovieUseCase,
    private val localUseCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUIState())
    val uiState: StateFlow<FavoriteUIState> = _uiState.asStateFlow()

    suspend fun loadFavorite() {
        _uiState.update { it.copy(isLoading = true) }
        
        localUseCase.getFavorite()
            .catch { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true to e.message.toString()
                    )
                }
            }
            .collect { result ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        moviesResponse = result?.toImmutableList() ?: persistentListOf()
                    )
                }
            }

    }

    fun hideError() {
        _uiState.update { it.copy(isError = false to "") }
    }
}