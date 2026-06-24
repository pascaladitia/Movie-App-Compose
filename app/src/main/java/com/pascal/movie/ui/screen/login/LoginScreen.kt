package com.pascal.movie.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pascal.movie.R
import com.pascal.movie.data.prefs.PreferencesLogin
import com.pascal.movie.ui.component.button.ButtonComponent
import com.pascal.movie.ui.component.form.FormEmailComponent
import com.pascal.movie.ui.component.form.FormPasswordComponent
import com.pascal.movie.ui.theme.MovieTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            PreferencesLogin.setIsAdmin(context, true)
            onLoginSuccess()
        }
    }

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Movie App",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to your account",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            FormEmailComponent(
                title = "Email",
                hintText = "Masukkan email",
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                isError = uiState.isEmailError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormPasswordComponent(
                title = "Password",
                hintText = "Masukkan password",
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                isError = uiState.isPasswordError,
                isPasswordVisible = uiState.isPasswordVisible,
                onIconClick = viewModel::onTogglePasswordVisibility,
                onEnter = viewModel::login
            )

            if (uiState.isError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.error
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            ButtonComponent(
                text = if (uiState.isLoading) "Loading..." else "Masuk",
                onClick = viewModel::login,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    MovieTheme(darkTheme = true) {
        LoginScreen(
            paddingValues = PaddingValues(),
            onLoginSuccess = {}
        )
    }
}
