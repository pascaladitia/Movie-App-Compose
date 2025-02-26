package com.pascal.movie.domain.model.detail

import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Double,
    val username: String
)