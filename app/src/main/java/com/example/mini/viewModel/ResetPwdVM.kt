package com.example.mini.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini.supabase.supabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class ResetPwdVM : ViewModel() {
    var pwd by mutableStateOf("")
        private set
    var confirmPwd by mutableStateOf("")
        private set
    var passwordVisible by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    val pwdMatch: Boolean
        get() = confirmPwd.isNotEmpty() && pwd == confirmPwd

    fun onPwdChange(value: String) {
        pwd = value
        errorMessage = null
    }

    fun onConfirmPwdChange(value: String) {
        confirmPwd = value
        errorMessage = null
    }

    fun togglePwdVisibility() {
        passwordVisible = !passwordVisible
    }

    fun updatePwd(onSuccess: () -> Unit) {
        if (isLoading) return

        errorMessage = when {
            pwd.length !in 8..20 -> "Password must be 8-20 characters"
            confirmPwd.isBlank() -> "Please confirm your password"
            pwd != confirmPwd -> "Passwords do not match"
            else -> null
        }

        if (errorMessage != null) return

        viewModelScope.launch {
            isLoading = true
            try {
                val newPwd = pwd
                supabaseClient.client.auth.updateUser {
                    pwd = newPwd
                }
                pwd = ""
                confirmPwd = ""
                errorMessage = null
                onSuccess()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unable to update password"
            } finally {
                isLoading = false
            }
        }
    }
}