package com.pascal.movie.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.remote.KtorClientApi
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.movie.emptyMovies

class MoviesPagingSource(
    private val localDataSource: LocalRepository,
    private val selection: Int
) : PagingSource<Int, Movies>() {

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {

            val page = params.key ?: 1
            val moviesList = when (selection) {
                1 -> KtorClientApi.getTrendingAll(page).results
                2 -> KtorClientApi.getTopRatedMovies(page).results
                3 -> KtorClientApi.getNowPlaying(page).results
                4 -> KtorClientApi.getUpcoming(page).results
                5 -> KtorClientApi.getTvShow(page).results
                6 -> KtorClientApi.getMovies(page).results
                7 -> if (page == 1) localDataSource.getFavoriteMovies()?.map {
                    emptyMovies.copy(id = it.id, poster_path = it.posterPath)
                }?.toList() ?: emptyList() else emptyList()

                else -> emptyList()
            }

            LoadResult.Page(
                data = moviesList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (moviesList.isEmpty()) null else page.plus(1),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}