package com.pascal.movie.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.ui.theme.MovieTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinInject<HomeViewModel>(),
    onDetail: () -> Unit
) {
    var isContentVisible by remember { mutableStateOf(false) }
    val movies: LazyPagingItems<Movies> = viewModel.movies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadMovies(0)
        delay(200)
        isContentVisible = true
    }

    HomeContent(
        isContentVisible = isContentVisible,
        movies = movies,
        onDetail = {

        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    movies: LazyPagingItems<Movies>? = null,
    onDetail: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val tabTitles = listOf("Trending", "New", "Movies", "Serieals", "TV Shows")
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabTitles.size }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 56.dp)
    ) {
        ScrollableTabRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            edgePadding = 0.dp,
            divider = {},
            indicator = {}
        ) {
            tabTitles.forEachIndexed { index, title ->
                Box {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = title,
                        style = if (pagerState.currentPage == index) MaterialTheme.typography.titleMedium
                        else MaterialTheme.typography.bodySmall,
                        color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (tabTitles[page]) {
                "Trending" -> LazyRowCorousel(movies = movies)
                "New" -> LazyRowCorousel(movies = movies)
                "Movies" -> LazyRowCorousel(movies = movies)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "For you",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                "See all",
                style = MaterialTheme.typography.bodySmall
            )
        }

    }

}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MovieTheme {
        HomeContent(
            movies = null,
            onDetail = {}
        )
    }
}