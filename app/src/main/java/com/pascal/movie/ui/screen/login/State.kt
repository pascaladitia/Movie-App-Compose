package com.pascal.movie.ui.screen.login

data class LoginUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isLoginSuccess: Boolean = false
)

data class LoginUIEvent(
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onTogglePasswordVisibility: () -> Unit = {},
    val onLogin: () -> Unit = {}
)
