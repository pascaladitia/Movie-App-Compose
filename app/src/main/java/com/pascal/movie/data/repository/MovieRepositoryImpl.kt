package com.pascal.movie.data.repository

import ReviewsCatalogResponse
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import com.pascal.movie.data.remote.api.KtorClientApi
import com.pascal.movie.data.remote.dtos.dashboard.DashboardResponse
import com.pascal.movie.data.remote.dtos.movie.MoviesResponse
import com.pascal.movie.data.remote.dtos.video.VideosCatalogResponse
import com.pascal.movie.domain.usecase.pagination.MoviesPagingSource
import com.pascal.movie.ui.screen.home.MovieTab
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class MovieRepositoryImpl(
    private val localDataSource: LocalRepositoryImpl,
) : MovieRepository {
    override suspend fun dashboard(): DashboardResponse {
        return KtorClientApi.dashboard()
    }

    override suspend fun getMoviesCatalog(numPage: Int?): List<MoviesResponse> {
        return KtorClientApi.getMovies(numPage).results.orEmpty()
    }

    override suspend fun getMovies(selection: MovieTab): Flow<PagingData<MoviesResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = {
                MoviesPagingSource(localDataSource, selection)
            }
        ).flow
    }

    override suspend fun getSingleMovie(id: Int): MoviesResponse {
        return KtorClientApi.getSingleMovie(id)
    }

    override suspend fun getMovieVideos(id: Int): VideosCatalogResponse {
        return KtorClientApi.getVideos(id)
    }

    override suspend fun getMovieReviews(id: Int): ReviewsCatalogResponse {
        return KtorClientApi.getReviews(id)
    }
}