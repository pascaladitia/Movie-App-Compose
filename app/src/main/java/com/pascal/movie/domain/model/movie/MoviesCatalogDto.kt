package com.pascal.movie.domain.model.movie

import kotlinx.serialization.Serializable

@Serializable
data class MoviesCatalogDto(
    val page: Int,
    val results: List<Movies>,
    val total_pages: Int,
    val total_results: Int
)