package com.pascal.movie.data.remote

import com.pascal.movie.BuildConfig
import com.pascal.movie.domain.model.dashboard.ResponseDashboard
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.movie.MoviesCatalogDto
import com.pascal.movie.domain.model.video.VideosCatalog
import com.pascal.movieku_compose.data.remote.dtos.ReviewsCatalog
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import org.koin.core.annotation.Single

@Single
object KtorClientApi {
    suspend fun dashboard(): ResponseDashboard {
        return client.get("http:///dashboard").body()
    }

    suspend fun getMovies(page: Int? = null): MoviesCatalogDto {
        return client.get("${BuildConfig.BASE_URL}/3/movie/popular"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTopRatedMovies(page: Int? = null): MoviesCatalogDto {
        return client.get("${BuildConfig.BASE_URL}/3/movie/top_rated"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getNowPlaying(page: Int? = null): MoviesCatalogDto {
        return client.get("${BuildConfig.BASE_URL}/3/movie/now_playing"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getUpcoming(page: Int? = null): MoviesCatalogDto {
        return client.get("${BuildConfig.BASE_URL}/3/movie/upcoming"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTvShow(page: Int? = null): MoviesCatalogDto {
        return client.get("${BuildConfig.BASE_URL}/3/trending/tv/day"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTrendingAll(page: Int? = null): MoviesCatalogDto {
        return client.get("${BuildConfig.BASE_URL}/3/trending/all/day"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getReviews(id: Int): ReviewsCatalog {
        return client.get("${BuildConfig.BASE_URL}/3/movie/{$id}/reviews"){
            parameter("api_key", BuildConfig.API_KEY)
        }.body()
    }

    suspend fun getVideos(id: Int): VideosCatalog {
        return client.get("${BuildConfig.BASE_URL}/3/movie/{$id}/videos"){
            parameter("api_key", BuildConfig.API_KEY)
        }.body()
    }

    suspend fun getSingleMovie(id: Int): Movies {
        return client.get("${BuildConfig.BASE_URL}/3/movie/{$id}"){
            parameter("api_key", BuildConfig.API_KEY)
        }.body()
    }
}