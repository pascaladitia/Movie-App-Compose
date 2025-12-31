package com.pascal.movie.domain.usecase.local

import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import com.pascal.movie.domain.mapper.toDomain
import com.pascal.movie.domain.mapper.toEntity
import com.pascal.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class LocalUseCaseImpl(
    private val repository: LocalRepositoryImpl,
) : LocalUseCase {
    override suspend fun insertFavorite(entity: Movie) {
        repository.insertFavorite(entity.toEntity())
    }

    override suspend fun deleteFavorite(entity: Movie) {
        repository.deleteFavorite(entity.toEntity())
    }

    override suspend fun getFavorite(): Flow<List<Movie>?> = flow {
        emit(repository.getFavorite()?.map { it.toDomain() })
    }

    override suspend fun getFavorite(id: Int): Boolean {
        return repository.getFavorite(id)
    }

    override suspend fun clearFavorite() {
        return repository.clearFavorite()
    }
}