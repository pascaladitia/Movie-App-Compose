package com.pascal.movie.data.remote.dtos.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosCatalogResponse(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("results")
    val results: List<VideosResponse>? = null
)

@Serializable
data class VideosResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("iso_3166_1")
    val iso3166_1: String? = null,

    @SerialName("iso_639_1")
    val iso639_1: String? = null,

    @SerialName("key")
    val key: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("official")
    val official: Boolean? = null,

    @SerialName("published_at")
    val publishedAt: String? = null,

    @SerialName("site")
    val site: String? = null,

    @SerialName("size")
    val size: Int? = null,

    @SerialName("type")
    val type: String? = null
)
