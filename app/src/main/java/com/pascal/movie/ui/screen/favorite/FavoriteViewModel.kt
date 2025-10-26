package com.pascal.movie.ui.screen.favorite

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import com.pascal.movie.data.repository.MovieRepositoryImpl

class FavoriteViewModel(
    private val repositoryImpl: MovieRepositoryImpl,
    private val database: LocalRepositoryImpl
) : ViewModel() {


}