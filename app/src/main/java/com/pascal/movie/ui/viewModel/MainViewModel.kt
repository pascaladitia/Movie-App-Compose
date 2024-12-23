package com.pascal.movie.ui.viewModel

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val repository: Repository,
    private val database: LocalRepository,
) : ViewModel() {

}