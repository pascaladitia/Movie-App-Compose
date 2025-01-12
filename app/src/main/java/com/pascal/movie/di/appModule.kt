package com.pascal.movie.di

import androidx.room.Room
import com.pascal.movie.data.local.database.AppDatabase
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import com.pascal.movie.ui.screen.detail.DetailViewModel
import com.pascal.movie.ui.screen.home.HomeViewModel
import com.pascal.movie.ui.screen.favorite.FavoriteViewModel
import com.pascal.movie.ui.screen.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder<AppDatabase>(
            androidContext(), androidContext().getDatabasePath("app.db").absolutePath)
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
    single { LocalRepository(get()) }
    single{ Repository(get()) }
    singleOf(::HomeViewModel)
    singleOf(::FavoriteViewModel)
    singleOf(::ProfileViewModel)
    singleOf(::DetailViewModel)
}