package com.pascal.movie.data.repository

import androidx.paging.PagingData
import com.pascal.movie.domain.model.dashboard.ResponseDashboard
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.video.VideosCatalog
import com.pascal.movie.domain.model.review.ReviewsCatalog
import kotlinx.coroutines.flow.Flow

interface RepositoryImpl {
    suspend fun dashboard() : ResponseDashboard
    suspend fun getMoviesCatalog(numPage:Int?): List<Movies>
    suspend fun getMovies(selection: Int): Flow<PagingData<Movies>>
    suspend fun getSingleMovie(id: Int): Movies
    suspend fun getMovieVideos(id: Int): VideosCatalog
    suspend fun getMovieReviews(id: Int): ReviewsCatalog

}