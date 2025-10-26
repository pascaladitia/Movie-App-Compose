package com.pascal.movie.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import com.pascal.movie.data.repository.MovieRepositoryImpl

class ProfileViewModel(
    private val repositoryImpl: MovieRepositoryImpl,
    private val database: LocalRepositoryImpl
) : ViewModel() {


}