package com.pascal.movie.data.repository

import ReviewsCatalogResponse
import androidx.paging.PagingData
import com.pascal.movie.data.remote.dtos.dashboard.DashboardResponse
import com.pascal.movie.data.remote.dtos.movie.MoviesResponse
import com.pascal.movie.data.remote.dtos.video.VideosCatalogResponse
import com.pascal.movie.ui.screen.home.MovieTab
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun dashboard() : DashboardResponse
    suspend fun getMoviesCatalog(numPage:Int?): List<MoviesResponse>
    suspend fun getMovies(selection: MovieTab): Flow<PagingData<MoviesResponse>>
    suspend fun getSingleMovie(id: Int): MoviesResponse
    suspend fun getMovieVideos(id: Int): VideosCatalogResponse
    suspend fun getMovieReviews(id: Int): ReviewsCatalogResponse
}