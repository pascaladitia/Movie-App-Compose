package com.pascal.movie.domain.usecase.local

import com.pascal.movie.data.local.entity.ProfileEntity
import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class LocalUseCaseImpl(
    private val localUseCase: LocalRepositoryImpl,
) : LocalUseCase {
    override fun getProfileById(id: Long): Flow<ProfileEntity?> {
        TODO("Not yet implemented")
    }

    override fun getAllProfiles(): Flow<List<ProfileEntity>> {
        TODO("Not yet implemented")
    }

    override fun deleteProfileById(item: ProfileEntity): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun insertProfile(item: ProfileEntity): Flow<Unit> {
        TODO("Not yet implemented")
    }

}