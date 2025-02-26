package com.pascal.movie.domain.model.video

import kotlinx.serialization.Serializable

@Serializable
data class VideosCatalog(
    val id: Int? = null,
    val results: List<Videos>? = null
)