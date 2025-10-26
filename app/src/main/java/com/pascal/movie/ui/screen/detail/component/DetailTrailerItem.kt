package com.pascal.movie.ui.screen.detail.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.pascal.movie.R
import com.pascal.movie.domain.model.Video
import com.pascal.movie.utils.Constant.YOUTUBE_TN_URL
import com.pascal.movie.utils.Constant.YOUTUBE_TRAILERS_URL
import com.pascal.movie.utils.intentActionView
import kotlinx.coroutines.delay

@Composable
fun DetailTrailerItem(
    modifier: Modifier = Modifier,
    item: Video? = null,
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

    val urlThumbnail = YOUTUBE_TN_URL + item?.key + "/hqdefault.jpg"
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
                        },
                        onLongPress = {
                            val trailerUrl = YOUTUBE_TRAILERS_URL + item?.key
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