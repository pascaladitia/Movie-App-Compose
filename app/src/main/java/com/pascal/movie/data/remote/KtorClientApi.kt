package com.pascal.movie.data.remote

import com.pascal.movie.domain.model.dashboard.ResponseDashboard
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.movie.MoviesCatalogDto
import com.pascal.movie.domain.model.video.VideosCatalog
import com.pascal.movieku_compose.data.remote.dtos.ReviewsCatalog
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
object KtorClientApi {
    suspend fun dashboard(): ResponseDashboard {
        return client.get("http:///dashboard").body()
    }

    suspend fun getMovies(page: Int? = null): MoviesCatalogDto {
        return client.get("/3/movies/popular"){
            parameter("page", page)
        }.body()
    }

    suspend fun getTopRatedMovies(page: Int? = null): MoviesCatalogDto {
        return client.get("/3/movies/top_rated"){
            parameter("page", page)
        }.body()
    }

    suspend fun getReviews(id: Int): ReviewsCatalog {
        return client.get("/3/movies/{$id}/reviews").body()
    }

    suspend fun getVideos(id: Int): VideosCatalog {
        return client.get("/3/movies/{$id}/videos").body()
    }

    suspend fun getSingleMovie(id: Int): Movies {
        return client.get("/3/movies/{$id}").body()
    }
}