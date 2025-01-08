package com.pascal.movie.ui.screen.home

import android.graphics.Movie
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.pascal.movie.R
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.ui.component.screenUtils.ShimmerAnimation
import com.pascal.movie.ui.screen.home.component.LazyRowCorousel
import com.pascal.movie.ui.theme.MovieTheme
import com.pascal.movie.utils.Constant.POSTER_BASE_URL
import com.pascal.movie.utils.Constant.W185
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
    val coroutineScope = rememberCoroutineScope()
    var isContentVisible by remember { mutableStateOf(false) }
    val movies: LazyPagingItems<Movies> = viewModel.movies.collectAsLazyPagingItems()
    val movies2: LazyPagingItems<Movies> = viewModel.movies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.loadMovies(1)
        viewModel.loadMovies2(6)
        delay(200)
        isContentVisible = true
    }

    HomeContent(
        isContentVisible = isContentVisible,
        movies = movies,
        movies2 = movies2,
        onCategory = {
            coroutineScope.launch {
                viewModel.loadMovies(it)
            }
        },
        onDetail = {

        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    movies: LazyPagingItems<Movies>? = null,
    movies2: LazyPagingItems<Movies>? = null,
    onCategory: (Int) -> Unit,
    onDetail: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val tabTitles = listOf("Trending", "Top Rated", "Now Playing", "Upcoming", "TV Shows")
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabTitles.size }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 56.dp)
            .verticalScroll(rememberScrollState())
            .animateContentSize()
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
                                    onCategory(index.plus(1))
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
                "Top Rated" -> LazyRowCorousel(movies = movies)
                "Now Playing" -> LazyRowCorousel(movies = movies)
                "Upcoming" -> LazyRowCorousel(movies = movies)
                "TV Shows" -> LazyRowCorousel(movies = movies)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            movies2?.itemCount?.let {
                items(count = it) { index ->
                    movies2[index]?.let {
                        MovieItemGrid(
                            item = it,
                            onMovieClicked = {
                                onDetail(it)
                            }
                        )
                    }
                }

                when {
                    movies2.loadState.refresh is LoadState.Loading || movies2.loadState.append is LoadState.Loading -> {
                        repeat(6) {
                            item {
                                ShimmerAnimation()
                            }
                        }
                    }

                    movies2.loadState.refresh is LoadState.Error || movies2.loadState.append is LoadState.Error -> {
                        //handle error
                    }
                }
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun MovieItemGrid(
    modifier: Modifier = Modifier,
    item: Movies,
    onMovieClicked: (Int) -> Unit
) {
    val url: String = POSTER_BASE_URL + W185 + item.poster_path
    val context = LocalContext.current
    val model = remember {
        ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .error(R.drawable.no_thumbnail)
            .placeholder(R.color.gray)
            .build()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onMovieClicked.invoke(item.id)
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = model,
            contentDescription = item.title,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(150.dp)
                .heightIn(min = 220.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MovieTheme {
        HomeContent(
            movies = null,
            onCategory = {},
            onDetail = {}
        )
    }
}