package com.pascal.movie.domain.model

data class AuthorDetails(
    val avatarPath: String,
    val name: String,
    val rating: Double,
    val username: String
)

data class Review(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)

data class ReviewsCatalog(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int
)
