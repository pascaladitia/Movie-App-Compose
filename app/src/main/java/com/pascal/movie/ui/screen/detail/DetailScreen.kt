package com.pascal.movie.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.pascal.movie.R
import com.pascal.movie.data.local.entity.FavoritesEntity
import com.pascal.movie.domain.base.UiState
import com.pascal.movie.domain.model.mapping.MovieDetailMapping
import com.pascal.movie.domain.model.movie.Movies
import com.pascal.movie.domain.model.video.Videos
import com.pascal.movie.ui.theme.MovieTheme
import com.pascal.movie.utils.Constant.POSTER_BASE_URL
import com.pascal.movie.utils.Constant.W185
import com.pascal.movie.utils.Constant.YOUTUBE_TN_URL
import com.pascal.movie.utils.Constant.YOUTUBE_TRAILERS_URL
import com.pascal.movie.utils.intentActionView
import compose.icons.FeatherIcons
import compose.icons.feathericons.ChevronLeft
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    movies: Movies? = null,
    viewModel: DetailViewModel = koinViewModel(),
    onNavBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isContentVisible by remember { mutableStateOf(false) }

    val moviesDetailState by viewModel.movieDetailUiState.collectAsState()
    var moviesDetail by remember { mutableStateOf<MovieDetailMapping?>(null) }

    LaunchedEffect(Unit) {
        isContentVisible = true
        viewModel.loadDetailMovie(movies)
    }

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        if (moviesDetail != null) {
            DetailContent(
                isContentVisible = isContentVisible,
                item = moviesDetail,
                onFavorite = { item, isFav ->
                    coroutineScope.launch {
                        viewModel.updateFavMovie(item, isFav)
                    }
                },
                onNavBack = {
                    onNavBack()
                }
            )
        }
    }

    LaunchedEffect(moviesDetailState) {
        when (moviesDetailState) {
            is UiState.Empty -> {}
            is UiState.Loading -> {}
            is UiState.Error -> {
                val errorState = moviesDetailState as UiState.Error
            }

            is UiState.Success -> {
                val data = (moviesDetailState as UiState.Success).data
                moviesDetail = data
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    item: MovieDetailMapping? = null,
    onFavorite: (FavoritesEntity, Boolean) -> Unit,
    onNavBack: () -> Unit
) {
    var hasAnimated by remember { mutableStateOf(false) }
    var favBtnClicked by rememberSaveable {
        mutableStateOf(item?.favorite ?: false)
    }

    val url: String = POSTER_BASE_URL + W185 + item?.movies?.poster_path
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable { onNavBack() },
                imageVector = FeatherIcons.ChevronLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable {
                        favBtnClicked = !favBtnClicked
                        onFavorite(
                            FavoritesEntity(
                                item?.movies?.id ?: 0,
                                item?.movies?.poster_path ?: ""
                            ),
                            favBtnClicked
                        )
                    },
                imageVector = if (favBtnClicked) Icons.Default.Favorite
                                else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
        }

        Spacer(Modifier.height(24.dp))

        AsyncImage(
            model = model,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = item?.movies?.title ?: "Empty Title",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = item?.movies?.release_date ?: "Empty Date",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailContentItem(
                modifier = Modifier.weight(1f),
                title = "popularity",
                value = "${item?.movies?.popularity ?: 0}"
            )

            DetailContentItem(
                modifier = Modifier.weight(1f),
                title = "Language",
                value = item?.movies?.original_language ?: "En"
            )

            DetailContentItem(
                modifier = Modifier.weight(1f),
                title = "vote",
                value = "${item?.movies?.vote_count ?: 0}"
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = item?.movies?.overview ?: "No Description",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(24.dp))

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
                    "Trailer",
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

        Spacer(Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(item?.videos ?: emptyList()) { index, data ->
                DetailTrailerItem(
                    item = data,
                    index = index,
                    isContentVisible = isContentVisible,
                    hasAnimated = hasAnimated,
                    onAnimated = {
                        hasAnimated = true
                    }
                )
            }
        }
    }
}

@Composable
fun DetailContentItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun DetailTrailerItem(
    modifier: Modifier = Modifier,
    item: Videos,
    index: Int,
    isContentVisible: Boolean = true,
    hasAnimated: Boolean,
    onAnimated: () -> Unit
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

    val urlThumbnail = YOUTUBE_TN_URL + item.key + "/hqdefault.jpg"
    val context = LocalContext.current
    val model = remember {
        ImageRequest.Builder(context)
            .data(urlThumbnail)
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

                            val trailerUrl = YOUTUBE_TRAILERS_URL + item.key
                            intentActionView(context, trailerUrl)
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = model,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(200.dp)
                    .height(110.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    MovieTheme {
        DetailContent(
            onFavorite = { item, isFav -> }
        ) { }
    }
}