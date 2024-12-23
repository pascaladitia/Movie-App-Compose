package com.pascal.movie.ui.screen.teams

import androidx.lifecycle.ViewModel
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository

class TeamViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {


}