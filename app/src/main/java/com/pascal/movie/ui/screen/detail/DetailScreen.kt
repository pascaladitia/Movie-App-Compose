package com.pascal.movie.ui.screen.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pascal.movie.ui.theme.MovieTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    id: String = "",
    viewModel: DetailViewModel = koinViewModel(),
    onNavBack: () -> Unit
) {

}


@Preview(showBackground = true)
@Composable
private fun DetailPreview() {
    MovieTheme {  }
}