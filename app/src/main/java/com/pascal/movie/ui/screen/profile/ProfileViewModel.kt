package com.pascal.movie.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository

class ProfileViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {


}