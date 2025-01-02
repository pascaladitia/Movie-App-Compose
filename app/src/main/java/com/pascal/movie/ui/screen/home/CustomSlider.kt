package com.pascal.movie.ui.screen.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.pascal.movie.R
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.utils.Constant.POSTER_BASE_URL
import com.pascal.movie.utils.Constant.W185
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    sliderList: MutableList<String>,
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
) {
    val pagerState = rememberPagerState(pageCount = { sliderList.size })
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)
                val transX = with(density) { (pageOffset * 100.dp).toPx() }
                val zIndex = 1f - pageOffset.absoluteValue

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                            translationX = transX
                        }
                        .zIndex(zIndex)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(imageCornerRadius))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .scale(Scale.FILL)
                            .crossfade(true)
                            .data(sliderList[page])
                            .build(),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.no_thumbnail),
                        modifier = Modifier
                            .height(imageHeight)
                    )
                }
            }
        }

        Row(
            modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(sliderList.size) {
                val color = if (pagerState.currentPage == it) dotsActiveColor else dotsInActiveColor
                Box(
                    modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(dotsSize)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyRowCorousel(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movies>?,
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 6.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 350.dp,
) {
    if (movies == null || movies.itemCount == 0) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Empty",
                style = MaterialTheme.typography.bodySmall
            )
        }
        return
    }

    var movie by remember { mutableStateOf<Movies?>(null) }

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { movies.itemCount }
    )
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier
            ) { page ->
                movie = movies[pagerState.currentPage]
                val result = movies[page]

                val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)
                val transX = with(density) { (pageOffset * 100.dp).toPx() }
                val zIndex = 1f - pageOffset.absoluteValue

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                            translationX = transX
                        }
                        .zIndex(zIndex)
                        .padding(10.dp)
                        .shadow(20.dp)
                        .clip(RoundedCornerShape(imageCornerRadius))
                ) {
                    val url: String = POSTER_BASE_URL + W185 + result?.poster_path

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .crossfade(true)
                            .error(R.drawable.no_thumbnail)
                            .placeholder(R.drawable.loading)
                            .build(),
                        contentDescription = result?.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(imageHeight)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            movie?.title ?: "",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        Text(
            movie?.release_date ?: "",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val maxDots = 5
            val startIndex = (pagerState.currentPage / maxDots) * maxDots

            repeat(maxDots) { index ->
                val actualIndex = startIndex + index

                val size by animateDpAsState(
                    targetValue = if (pagerState.currentPage == actualIndex) dotsSize * 1.5f else dotsSize,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                val color by animateColorAsState(
                    targetValue = if (pagerState.currentPage == actualIndex) dotsActiveColor else dotsInActiveColor,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                Box(
                    modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(size)
                        .background(color)
                        .clickable {
                            if (actualIndex < movies.itemCount) {
                                scope.launch {
                                    pagerState.animateScrollToPage(actualIndex)
                                }
                            }
                        }
                )
            }
        }
    }
}



