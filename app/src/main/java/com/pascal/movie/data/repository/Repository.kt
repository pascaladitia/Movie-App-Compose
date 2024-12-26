package com.pascal.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.remote.KtorClientApi
import com.pascal.movie.domain.model.dashboard.ResponseDashboard
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.video.VideosCatalog
import com.pascal.movie.domain.pagination.MoviesPagingSource
import com.pascal.movieku_compose.data.remote.dtos.ReviewsCatalog
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class Repository(
    private val localDataSource: LocalRepository,
) : RepositoryImpl {
    override suspend fun dashboard(): ResponseDashboard {
        return KtorClientApi.dashboard()
    }

    override suspend fun getMoviesCatalog(numPage: Int?): List<Movies> {
        return KtorClientApi.getMovies(numPage).results
    }

    override suspend fun getMovies(selection: Int): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                MoviesPagingSource(localDataSource, selection)
            }
        ).flow
    }

    override suspend fun getSingleMovie(id: Int): Movies {
        return KtorClientApi.getSingleMovie(id)
    }

    override suspend fun getMovieVideos(id: Int): VideosCatalog {
        return KtorClientApi.getVideos(id)
    }

    override suspend fun getMovieReviews(id: Int): ReviewsCatalog {
        return KtorClientApi.getReviews(id)
    }
}