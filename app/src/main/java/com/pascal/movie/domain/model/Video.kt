package com.pascal.movie.domain.model

data class Video(
    val id: String,
    val iso3166_1: String,
    val iso639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)

data class VideosCatalog(
    val id: Int,
    val results: List<Video>
)
