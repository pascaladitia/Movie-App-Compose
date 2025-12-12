package com.pascal.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pascal.movie.data.local.dao.FavoritesDao
import com.pascal.movie.data.local.dao.ProfileDao
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.entity.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        FavoritesEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun favoritesDao(): FavoritesDao
}

