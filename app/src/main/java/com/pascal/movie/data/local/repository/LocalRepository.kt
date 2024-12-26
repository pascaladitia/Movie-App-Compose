package com.pascal.movie.data.local.repository

import com.pascal.movie.data.local.database.AppDatabase
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.entity.ProfileEntity
import org.koin.core.annotation.Single

@Single
class LocalRepository(
    private val database: AppDatabase,
) : LocalRepositoryImpl {

    // Profile
    override suspend fun getProfileById(id: Long): ProfileEntity? {
        return database.profileDao().getProfileById(id)
    }

    override suspend fun getAllProfiles(): List<ProfileEntity> {
        return database.profileDao().getAllProfiles()
    }

    override suspend fun deleteProfileById(item: ProfileEntity) {
        return database.profileDao().deleteProfile(item)
    }

    override suspend fun insertProfile(item: ProfileEntity) {
        return database.profileDao().insertProfile(item)
    }

    // Favorites
    suspend fun insertFavoriteItem(entity: FavoritesEntity) {
        database.favoritesDao().insertFavorite(entity)
    }

    suspend fun deleteFavoriteItem(entity: FavoritesEntity) {
        database.favoritesDao().deleteFavorite(entity)
    }

    suspend fun getFavoriteMovies(): List<FavoritesEntity>? {
        return database.favoritesDao().getFavoriteMovieList()
    }

    suspend fun getFavoriteMovie(id: Int): Boolean {
        return (database.favoritesDao().getFavoriteMovie(id) != null)
    }
}