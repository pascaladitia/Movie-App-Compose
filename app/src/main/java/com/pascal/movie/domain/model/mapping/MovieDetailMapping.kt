package com.pascal.movie.domain.model.mapping

import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.review.Review
import com.pascal.movie.domain.model.video.Videos

class MovieDetailMapping(
    val review: List<Review>,
    val videos: List<Videos>,
    val movies: Movies,
    var favorite: Boolean
)