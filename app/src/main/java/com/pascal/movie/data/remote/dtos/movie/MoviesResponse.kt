package com.pascal.movie.data.remote.dtos.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("media_type")
    val mediaType: String? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null
)

val emptyMoviesResponse = MoviesResponse(
    adult = false,
    backdropPath = "",
    genreIds = emptyList(),
    id = 0,
    originalLanguage = "",
    originalTitle = "",
    overview = "",
    popularity = 0.0,
    posterPath = "",
    mediaType = "",
    releaseDate = "",
    title = "",
    video = false,
    voteAverage = 0.0,
    voteCount = 0
)
