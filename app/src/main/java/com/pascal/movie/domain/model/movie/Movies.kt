package com.pascal.movie.domain.model.movie

import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    val backdrop_path: String?,
    val id: Int,
    val title: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val media_type: String?,
    val adult: Boolean?,
    val original_language: String?,
    val genre_ids: List<Int>?,
    val popularity: Double?,
    val release_date: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)

val emptyMovies : Movies = Movies(
    adult = false,
    backdrop_path = "",
    genre_ids = emptyList(),
    id = 0,
    original_language = "",
    original_title = "",
    overview = "",
    popularity = 0.0,
    poster_path = "",
    media_type = "",
    release_date = "",
    title = "",
    video = false,
    vote_average = 0.0,
    vote_count = 0
)