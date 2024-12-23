package com.pascal.movie.data.repository

import com.pascal.movie.domain.model.dashboard.ResponseDashboard

interface RepositoryImpl {
    suspend fun dashboard() : ResponseDashboard

}