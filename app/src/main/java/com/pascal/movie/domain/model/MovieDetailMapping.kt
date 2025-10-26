package com.pascal.movie.domain.model

class MovieDetailMapping(
    val review: List<Review>? = null,
    val videos: List<Video>? = null,
    val movie: Movie? = null,
    var favorite: Boolean? = null
)