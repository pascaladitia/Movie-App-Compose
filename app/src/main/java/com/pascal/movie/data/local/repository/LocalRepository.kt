package com.pascal.movie.data.local.repository

import com.pascal.movie.data.local.entity.ProfileEntity


interface LocalRepository {
    suspend fun getProfileById(id: Long): ProfileEntity?
    suspend fun getAllProfiles(): List<ProfileEntity>
    suspend fun deleteProfileById(item: ProfileEntity)
    suspend fun insertProfile(item: ProfileEntity)
}