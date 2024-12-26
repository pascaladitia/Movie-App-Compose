package com.pascal.movie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favorite")
data class FavoritesEntity (
    @PrimaryKey
    val id: Int,
    val posterPath: String,
)
