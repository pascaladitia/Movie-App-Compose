package com.pascal.movie.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pascal.movie.data.local.repository.LocalRepository
import com.pascal.movie.data.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val repository: Repository,
    private val database: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState get() = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email, isEmailError = false, isError = false)
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(password = password, isPasswordError = false, isError = false)
        }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    fun login() {
        val state = _uiState.value
        var hasError = false

        if (state.email.isBlank()) {
            _uiState.update { it.copy(isEmailError = true) }
            hasError = true
        }
        if (state.password.isBlank()) {
            _uiState.update { it.copy(isPasswordError = true) }
            hasError = true
        }

        if (hasError) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000)

            if (state.email == "admin@admin.com" && state.password == "admin123") {
                _uiState.update {
                    it.copy(isLoading = false, isLoginSuccess = true)
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        message = "Email atau password salah"
                    )
                }
            }
        }
    }
}
