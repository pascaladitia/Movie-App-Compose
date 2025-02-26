package com.pascal.movie.domain.model.review

import com.pascal.movie.domain.model.detail.AuthorDetails
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val author: String? = null,
    val author_details: AuthorDetails? = null,
    val content: String? = null,
    val created_at: String? = null,
    val id: String? = null,
    val updated_at: String? = null,
    val url: String? = null
)