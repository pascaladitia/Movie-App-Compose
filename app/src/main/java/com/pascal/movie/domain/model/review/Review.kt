package com.pascal.movie.domain.model.review

import com.pascal.movie.domain.model.detail.AuthorDetails
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val author: String,
    val author_details: AuthorDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)