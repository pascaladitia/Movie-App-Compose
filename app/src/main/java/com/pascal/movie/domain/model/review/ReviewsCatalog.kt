package com.pascal.movieku_compose.data.remote.dtos

import com.pascal.movie.domain.model.review.Review

data class ReviewsCatalog(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)