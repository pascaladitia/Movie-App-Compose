package com.pascal.movie.di

import com.pascal.movie.data.repository.Repository
import com.pascal.movie.ui.screen.home.HomeViewModel
import com.pascal.movie.ui.screen.live.LiveViewModel
import com.pascal.movie.ui.screen.profile.ProfileViewModel
import com.pascal.movie.ui.viewModel.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single{ Repository() }

    singleOf(::MainViewModel)
    singleOf(::HomeViewModel)
    singleOf(::LiveViewModel)
    singleOf(::ProfileViewModel)
}