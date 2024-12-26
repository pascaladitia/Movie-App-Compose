package com.pascal.movie.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.pascal.movie.ui.theme.MovieTheme
import com.pascal.movie.utils.Constant.POSTER_BASE_URL
import com.pascal.movie.utils.Constant.W185
import kotlinx.coroutines.delay
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

//    HomeContent(
//        isContentVisible = isContentVisible,
//        movies = movies,
//        onDetail = {
//
//        }
//    )
    val sliderList = remember {
        mutableListOf(
            "https://www.gstatic.com/webp/gallery/1.webp",
            "https://www.gstatic.com/webp/gallery/2.webp",
            "https://www.gstatic.com/webp/gallery/3.webp",
            "https://www.gstatic.com/webp/gallery/4.webp",
            "https://www.gstatic.com/webp/gallery/5.webp",
        )
    }
    CustomSlider(sliderList = sliderList)
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    movies: LazyPagingItems<Movies>? = null,
    onDetail: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyRow (
            modifier = Modifier.fillMaxWidth()
        ) {
            items(count = movies?.itemCount ?: 0) { index ->
                movies?.get(index)?.let {
                    MovieItemGrid(it) {
                        onDetail(it)
                    }
                }
            }

            when {
                movies?.loadState?.refresh is LoadState.Loading || movies?.loadState?.append is LoadState.Loading -> {
                    repeat(6) {
                        item {
                            ShimmerAnimation()
                        }
                    }
                }

                movies?.loadState?.refresh is LoadState.Error || movies?.loadState?.append is LoadState.Error -> {
                    //handle error
                }
            }
        }
    }

}

@Composable
fun MovieItemGrid(item: Movies, onMovieClicked: (Int) -> Unit) {
    val url: String = POSTER_BASE_URL + W185 + item.poster_path
    val context = LocalContext.current
    val model = remember {
        ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .error(R.drawable.no_thumbnail)
            .build()
    }

    Box(
        modifier = Modifier
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
                .width(150.dp)
                .height(220.dp)
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
            onDetail = {}
        )
    }
}