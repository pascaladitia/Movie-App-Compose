package com.pascal.movie.ui.screen.detail.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pascal.movie.ui.component.screenUtils.ShimmerAnimation
import com.pascal.movie.ui.theme.MovieTheme

@Composable
fun DetailShimmerAnimation() {
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
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.9f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}

@Composable
fun ShimmerItem(modifier: Modifier = Modifier, brush: Brush) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(brush)
            )

            Spacer(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(brush)
            )
        }

        Spacer(Modifier.height(24.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(brush)
        )

        Spacer(Modifier.height(24.dp))

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(200.dp)
                .height(40.dp)
                .background(brush)
        )

        Spacer(Modifier.height(12.dp))

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(120.dp)
                .height(20.dp)
                .background(brush)
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContentItem(
                modifier = Modifier.weight(1f),
                brush = brush
            )

            ContentItem(
                modifier = Modifier.weight(1f),
                brush = brush
            )

            ContentItem(
                modifier = Modifier.weight(1f),
                brush = brush
            )
        }

        Spacer(Modifier.height(24.dp))

        Spacer(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(40.dp)
                .background(brush)
        )

        Spacer(Modifier.height(24.dp))

        Spacer(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(40.dp)
                .background(brush)
        )
    }
}

@Composable
fun ContentItem(
    modifier: Modifier = Modifier,
    brush: Brush
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(20.dp)
                .height(20.dp)
                .background(brush)
        )

        Spacer(Modifier.height(6.dp))

        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(80.dp)
                .height(20.dp)
                .background(brush)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShimmerPreview() {
    MovieTheme {
        DetailShimmerAnimation()
    }
}
