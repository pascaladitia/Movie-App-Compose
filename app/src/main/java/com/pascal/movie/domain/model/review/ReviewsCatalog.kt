package com.pascal.movie.domain.model.review

import kotlinx.serialization.Serializable

@Serializable
data class ReviewsCatalog(
    val id: Int? = null,
    val page: Int? = null,
    val results: List<Review>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)