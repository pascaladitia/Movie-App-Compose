package com.pascal.movie.data.local.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenreIds(list: List<Int>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toGenreIds(data: String): List<Int>? {
        return if (data.isEmpty()) null
        else data.split(",").map { it.toInt() }
    }
}