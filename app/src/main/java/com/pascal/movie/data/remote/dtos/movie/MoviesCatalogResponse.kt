package com.pascal.movie.data.remote.dtos.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesCatalogResponse(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<MoviesResponse>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)