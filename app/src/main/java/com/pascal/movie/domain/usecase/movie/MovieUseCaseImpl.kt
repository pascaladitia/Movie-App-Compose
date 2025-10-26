package com.pascal.movie.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.pascal.movie.data.repository.MovieRepositoryImpl
import com.pascal.movie.domain.mapper.toDomain
import com.pascal.movie.domain.model.Dashboard
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.domain.model.ReviewsCatalog
import com.pascal.movie.domain.model.VideosCatalog
import com.pascal.movie.ui.screen.home.MovieTab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class MovieUseCaseImpl(
    private val repository: MovieRepositoryImpl
) : MovieUseCase {

    override suspend fun dashboard(): Flow<Dashboard> = flow {
        emit(repository.dashboard().toDomain())
    }

    override suspend fun getMoviesCatalog(numPage: Int?): Flow<List<Movie>> = flow {
        val response = repository.getMoviesCatalog(numPage)
        emit(response.map { it.toDomain() })
    }

    override suspend fun getMovies(selection: MovieTab): Flow<PagingData<Movie>> {
        return repository.getMovies(selection).map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getSingleMovie(id: Int): Flow<Movie> = flow {
        emit(repository.getSingleMovie(id).toDomain())
    }

    override suspend fun getMovieVideos(id: Int): Flow<VideosCatalog> = flow {
        emit(repository.getMovieVideos(id).toDomain())
    }

    override suspend fun getMovieReviews(id: Int): Flow<ReviewsCatalog> = flow {
        emit(repository.getMovieReviews(id).toDomain())
    }
}
