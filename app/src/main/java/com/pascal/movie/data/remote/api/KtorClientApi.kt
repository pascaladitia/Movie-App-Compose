package com.pascal.movie.data.remote.api

import ReviewsCatalogResponse
import com.pascal.movie.BuildConfig
import com.pascal.movie.data.remote.client
import com.pascal.movie.data.remote.dtos.dashboard.DashboardResponse
import com.pascal.movie.data.remote.dtos.movie.MoviesCatalogResponse
import com.pascal.movie.data.remote.dtos.movie.MoviesResponse
import com.pascal.movie.data.remote.dtos.video.VideosCatalogResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
object KtorClientApi {
    suspend fun dashboard(): DashboardResponse {
        return client.get("http:///dashboard").body()
    }

    suspend fun getMovies(page: Int? = null): MoviesCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/popular"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTopRatedMovies(page: Int? = null): MoviesCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/top_rated"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getNowPlaying(page: Int? = null): MoviesCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/now_playing"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getUpcoming(page: Int? = null): MoviesCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/upcoming"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTvShow(page: Int? = null): MoviesCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/trending/tv/day"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getTrendingAll(page: Int? = null): MoviesCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/trending/all/day"){
            parameter("api_key", BuildConfig.API_KEY)
            parameter("page", page)
        }.body()
    }

    suspend fun getReviews(id: Int): ReviewsCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/$id/reviews"){
            parameter("api_key", BuildConfig.API_KEY)
        }.body()
    }

    suspend fun getVideos(id: Int): VideosCatalogResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/$id/videos"){
            parameter("api_key", BuildConfig.API_KEY)
        }.body()
    }

    suspend fun getSingleMovie(id: Int): MoviesResponse {
        return client.get("${BuildConfig.BASE_URL}/3/movie/$id"){
            parameter("api_key", BuildConfig.API_KEY)
        }.body()
    }
}