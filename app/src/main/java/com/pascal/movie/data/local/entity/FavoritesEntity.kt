package com.pascal.movie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favorite")
data class FavoritesEntity(
    @PrimaryKey
    val id: Int,
    val backdropPath: String? = null,
    val title: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val mediaType: String? = null,
    val adult: Boolean? = null,
    val originalLanguage: String? = null,
    val genreIds: List<Int>? = null,
    val popularity: Double? = null,
    val releaseDate: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)
