package com.pascal.movie.data.local.repository

import com.pascal.movie.data.local.database.AppDatabase
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.data.local.entity.ProfileEntity
import org.koin.core.annotation.Single

@Single
class LocalRepositoryImpl(
    private val database: AppDatabase,
) : LocalRepository {

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
    override suspend fun insertFavorite(entity: FavoritesEntity) {
        database.favoritesDao().insertFavorite(entity)
    }

    override suspend fun deleteFavorite(entity: FavoritesEntity) {
        database.favoritesDao().deleteFavorite(entity)
    }

    override suspend fun getFavorite(): List<FavoritesEntity>? {
        return database.favoritesDao().getFavoriteMovieList()
    }

    override suspend fun getFavorite(id: Int): Boolean {
        return database.favoritesDao().getFavoriteMovie(id) != null
    }

    override suspend fun clearFavorite() {
        return database.favoritesDao().clearFavoritesTable()
    }
}