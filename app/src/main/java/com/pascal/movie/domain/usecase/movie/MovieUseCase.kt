package com.pascal.movie.domain.usecase.movie

import androidx.paging.PagingData
import com.pascal.movie.domain.model.Dashboard
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.domain.model.ReviewsCatalog
import com.pascal.movie.domain.model.VideosCatalog
import com.pascal.movie.ui.screen.home.MovieTab
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    suspend fun dashboard(): Flow<Dashboard>
    suspend fun getMoviesCatalog(numPage: Int?): Flow<List<Movie>>
    suspend fun getMovies(selection: MovieTab): Flow<PagingData<Movie>>
    suspend fun getSingleMovie(id: Int): Flow<Movie>
    suspend fun getMovieVideos(id: Int): Flow<VideosCatalog>
    suspend fun getMovieReviews(id: Int): Flow<ReviewsCatalog>
}
