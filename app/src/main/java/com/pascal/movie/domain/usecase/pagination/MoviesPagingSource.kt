package com.pascal.movie.domain.usecase.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pascal.movie.data.local.repository.LocalRepositoryImpl
import com.pascal.movie.data.remote.api.KtorClientApi
import com.pascal.movie.data.remote.dtos.movie.MoviesResponse
import com.pascal.movie.data.remote.dtos.movie.emptyMoviesResponse
import com.pascal.movie.ui.screen.home.MovieTab

class MoviesPagingSource(
    private val localRepository: LocalRepositoryImpl,
    private val selection: MovieTab
) : PagingSource<Int, MoviesResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesResponse>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesResponse> {
        return try {
            val page = params.key ?: 1

            val moviesList: List<MoviesResponse> = when (selection) {
                MovieTab.TRENDING -> KtorClientApi.getTrendingAll(page).results.orEmpty()
                MovieTab.TOP_RATED -> KtorClientApi.getTopRatedMovies(page).results.orEmpty()
                MovieTab.NOW_PLAYING -> KtorClientApi.getNowPlaying(page).results.orEmpty()
                MovieTab.UPCOMING -> KtorClientApi.getUpcoming(page).results.orEmpty()
                MovieTab.TV_SHOWS -> KtorClientApi.getTvShow(page).results.orEmpty()
                MovieTab.FAVORITES -> if (page == 1) {
                    localRepository.getFavorite()?.map {
                        emptyMoviesResponse.copy(id = it.id, posterPath = it.posterPath)
                    }.orEmpty()
                } else emptyList()
            }

            LoadResult.Page(
                data = moviesList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (moviesList.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
