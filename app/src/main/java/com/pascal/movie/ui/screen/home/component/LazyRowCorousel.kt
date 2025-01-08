package com.pascal.movie.ui.screen.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.pascal.movie.R
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.ui.theme.LightGray
import com.pascal.movie.utils.Constant.POSTER_BASE_URL
import com.pascal.movie.utils.Constant.W185
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

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
        LazyRowShimmer()
        return
    }

    var movie by remember { mutableStateOf<Movies?>(null) }
    var baseMovie by remember { mutableStateOf<Movies?>(null) }
    var isSlide by remember { mutableStateOf(true) }

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { movies.itemCount }
    )
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    LaunchedEffect(baseMovie) {
        isSlide = false
        delay(300)
        movie = baseMovie
        isSlide = true
    }

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
                baseMovie = movies[pagerState.currentPage]
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
                        .shadow(20.dp, spotColor = LightGray)
                        .clip(RoundedCornerShape(imageCornerRadius))
                ) {
                    val url: String = POSTER_BASE_URL + W185 + result?.poster_path

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .crossfade(true)
                            .error(R.drawable.no_thumbnail)
                            .placeholder(R.color.gray)
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

        AnimatedVisibility(
            visible = isSlide,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically { fullHeight -> fullHeight }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = movie?.title ?: "Empty Title",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.height(12.dp))

        AnimatedVisibility(
            visible = isSlide,
            enter = fadeIn(tween(durationMillis = 300)) + slideInVertically(),
            exit = fadeOut(tween(durationMillis = 300)) + slideOutVertically()
        ) {
            Text(
                text = movie?.release_date ?: "Empty Date",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier.fillMaxWidth(),
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

@Composable
fun LazyRowShimmer(
    modifier: Modifier = Modifier,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 350.dp,
) {
    val transition = rememberInfiniteTransition(label = "transition")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ), label = ""
    )

    val shimmerColorShades = listOf(
        Color.LightGray,
        Color.Gray,
        Color.LightGray
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 }
    )
    val density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier
            ) { page ->
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
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(imageHeight)
                            .background(brush = brush)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(150.dp)
                .height(30.dp)
                .background(brush = brush)
        )

        Spacer(Modifier.height(12.dp))

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(80.dp)
                .height(20.dp)
                .background(brush = brush)
        )

        Spacer(Modifier.height(12.dp))

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(60.dp)
                .height(20.dp)
                .background(brush = brush)
        )

        Spacer(Modifier.height(16.dp))
    }
}




