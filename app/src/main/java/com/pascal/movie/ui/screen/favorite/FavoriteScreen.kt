package com.pascal.movie.ui.screen.favorite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.movie.R
import com.pascal.movie.domain.model.Movie
import com.pascal.movie.ui.component.dialog.ShowDialog
import com.pascal.movie.ui.screen.home.state.LocalHomeEvent
import com.pascal.movie.ui.theme.MovieTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: FavoriteViewModel = koinInject<FavoriteViewModel>(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDetail: (Movie) -> Unit
) {
    val event = LocalHomeEvent.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadFavorite()
    }

    if (uiState.isError.first) {
        ShowDialog(
            message = uiState.isError.second,
            textButton = stringResource(R.string.close)
        ) {
            viewModel.hideError()
        }
    }

    CompositionLocalProvider(
        LocalHomeEvent provides event.copy(
            onCategory = {
                coroutineScope.launch {
                    viewModel.loadFavorite()
                }
            },
            onDetail = onDetail
        )
    ) {
        Surface(
            modifier = modifier.padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            FavoriteContent(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                moviesResponse2 = uiState.moviesResponse
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FavoriteContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    moviesResponse2: ImmutableList<Movie>? = null,
) {
    val event = LocalHomeEvent.current
    val coroutine = rememberCoroutineScope()
    var isContentVisible by remember { mutableStateOf(false) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isContentVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .animateContentSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    MovieTheme { }
}