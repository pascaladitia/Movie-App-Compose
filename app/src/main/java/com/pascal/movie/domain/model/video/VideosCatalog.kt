package com.pascal.movie.domain.model.video

import kotlinx.serialization.Serializable

@Serializable
data class VideosCatalog(
    val id: Int,
    val results: List<Videos>
)