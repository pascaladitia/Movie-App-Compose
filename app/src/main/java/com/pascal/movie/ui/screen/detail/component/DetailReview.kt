package com.pascal.movie.ui.screen.detail.component

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.pascal.movie.domain.model.Review

@Composable
fun DetailReview(
    modifier: Modifier = Modifier,
    review: List<Review>
) {
    val context = LocalContext.current

    Text(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        text = "Reviews",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
    ) {
        review.forEach { data ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp)
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = data.content,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .clickable {
                            val reviewsUrl = data.url
                            val intent = Intent(Intent.ACTION_VIEW, reviewsUrl.toUri())
                            context.startActivity(intent, null)
                        }
                        .background(
                            Color.DarkGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(all = 8.dp)
                )
            }
        }
    }
}