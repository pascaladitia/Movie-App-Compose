package com.pascal.movie.domain.usecase.local

import com.pascal.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface LocalUseCase {
    suspend fun insertFavorite(entity: Movie)
    suspend fun deleteFavorite(entity: Movie)
    suspend fun getFavorite(): Flow<List<Movie>?>
    suspend fun getFavorite(id: Int): Boolean
    suspend fun clearFavorite()
}