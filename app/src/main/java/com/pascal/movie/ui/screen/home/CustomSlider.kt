package com.pascal.movie.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.pascal.movie.R
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


