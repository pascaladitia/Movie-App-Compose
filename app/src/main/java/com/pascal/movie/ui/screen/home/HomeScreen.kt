package com.pascal.movie.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.pascal.movie.R
import com.pascal.movie.domain.base.UiState
import com.pascal.movie.domain.model.movie.Movies
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
    onDetail: (Movies) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val movies: LazyPagingItems<Movies> = viewModel.movies.collectAsLazyPagingItems()

    val moviesState by viewModel.movies2.collectAsState()
    var movies2 by remember { mutableStateOf<List<Movies>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.loadMovies(1)
    }

    LaunchedEffect(Unit) {
        viewModel.loadMovies2()
    }

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        HomeContent(
            movies = movies,
            movies2 = movies2,
            onCategory = {
                coroutineScope.launch {
                    viewModel.loadMovies(it)
                }
            },
            onDetail = {
                onDetail(it)
            }
        )
    }

    LaunchedEffect(moviesState) {
        when (moviesState) {
            is UiState.Empty -> {}
            is UiState.Loading -> {}
            is UiState.Error -> {
                val errorState = moviesState as UiState.Error
            }

            is UiState.Success -> {
                val data = (moviesState as UiState.Success).data
                movies2 = data
            }
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movies>? = null,
    movies2: List<Movies>? = null,
    onCategory: (Int) -> Unit,
    onDetail: (Movies) -> Unit
) {
    val coroutine = rememberCoroutineScope()
    var isContentVisible by remember { mutableStateOf(false) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isContentVisible = true
    }

    val coroutineScope = rememberCoroutineScope()
    val tabTitles = listOf("Trending", "Top Rated", "Now Playing", "Upcoming", "TV Shows")
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabTitles.size }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .animateContentSize()
    ) {
        Spacer(Modifier.height(24.dp))

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(tween(1000)),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally(tween(1000)) { it }
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
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            LazyRowCorousel(
                isContentVisible = isContentVisible,
                movies = movies
            ) {
                coroutine.launch {
                    isContentVisible = false
                    delay(1000)
                    onDetail(it)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)),
                exit = fadeOut(tween(durationMillis = 500))
            ) {
                Text(
                    "For you",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)),
                exit = fadeOut(tween(durationMillis = 500))
            ) {
                Text(
                    "See all",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(movies2 ?: emptyList()) { index, data ->
                MovieItemGrid(
                    item = data,
                    index = index,
                    isContentVisible = isContentVisible,
                    hasAnimated = hasAnimated,
                    onAnimated = {
                        hasAnimated = true
                    },
                    onDetail = {
                        coroutine.launch {
                            isContentVisible = false
                            delay(1000)
                            onDetail(it)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun MovieItemGrid(
    modifier: Modifier = Modifier,
    item: Movies,
    index: Int,
    isContentVisible: Boolean = true,
    hasAnimated: Boolean,
    onAnimated: () -> Unit,
    onDetail: (Movies) -> Unit
) {
    var isAnimation by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = ""
    )

    LaunchedEffect(isContentVisible) {
        if (isContentVisible) {
            if (!hasAnimated) {
                delay(200 * index.toLong())
                isAnimation = true
                onAnimated()
            } else {
                isAnimation = true
            }
        } else {
            isAnimation = false
        }
    }

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

    AnimatedVisibility(
        visible = isAnimation,
        enter = fadeIn(tween(500)) + scaleIn(
            initialScale = 0.8f,
            animationSpec = tween(durationMillis = 1000)
        ),
        exit = fadeOut(tween(500)) + scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(durationMillis = 500)
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .scale(scale)
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                            onDetail.invoke(item)
                        }
                    )
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