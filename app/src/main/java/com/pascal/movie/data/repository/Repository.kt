package com.pascal.movie.data.repository

import com.pascal.movie.data.remote.KtorClientApi
import com.pascal.movie.domain.model.dashboard.ResponseDashboard
import org.koin.core.annotation.Single

@Single
class Repository : RepositoryImpl {
    override suspend fun dashboard(): ResponseDashboard {
        return KtorClientApi.dashboard()
    }
}