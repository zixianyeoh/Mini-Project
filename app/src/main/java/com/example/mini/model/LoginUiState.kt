package com.example.mini.model

data class LoginUiState(
    val emailOrPhone: String = "",
    val password: String = "",
    val rememberMe: Boolean = true,
    val passwordVisibility: Boolean = false,
    val errorMessage: String? = null
)
