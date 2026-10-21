package com.example.mini.viewModel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini.model.User
import com.example.mini.supabase.supabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.Phone
import kotlinx.coroutines.launch
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

class LoginVM : ViewModel() {
    var emailOrPhone by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisibility by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun onEmailOrPhoneChange(value: String) {
        emailOrPhone = value
        errorMessage = null
    }

    fun onPwdChange(value: String) {
        password = value
        errorMessage = null
    }

    fun togglePwdVisibility() {
        passwordVisibility = !passwordVisibility
    }

    private fun normalizePhone(value: String): String {
        val phone = value.filterNot {
            it.isWhitespace() || it == '-' || it == '(' || it == ')'
        }
        return when {
            phone.startsWith("+") -> phone
            phone.startsWith("0") -> "+60${phone.drop(1)}"
            phone.startsWith("60") -> "+$phone"
            else -> phone
        }
    }
    fun validate() : Boolean {
        val account = emailOrPhone.trim()
        val normalizedPhone = normalizePhone(account)

        errorMessage = when {
            account.isBlank()
                -> "Email or Phone is required"
            account.contains("@") &&
                    !Patterns.EMAIL_ADDRESS.matcher(account).matches()
                -> "Invalid email format"
            !account.contains("@") &&
                    !Regex("^\\+[1-9]\\d{7,14}$").matches(normalizedPhone)
                -> "Invalid phone number format"
            password.isBlank()
                -> "Password is required"
            else -> null
        }
        return errorMessage == null
    }

    fun signIn(onSuccess: (User) -> Unit) {
        if (isLoading || !validate()) return
        viewModelScope.launch {
            isLoading = true
            try {
                val account = emailOrPhone.trim()
                val loginPwd = password

                if (account.contains("@")) {
                    supabaseClient.client.auth.signInWith(Email){
                        email = account
                        password = loginPwd
                    }
                } else {
                    supabaseClient.client.auth.signInWith(Phone){
                        phone = normalizePhone(account)
                        password = loginPwd
                    }
                }

                val user = supabaseClient.client.auth.currentUserOrNull()
                    ?: error("Unable to retrieve the signed-in user")

                val username = user.userMetadata
                    ?.get("username")
                    ?.jsonPrimitive
                    ?.contentOrNull
                    ?: user.email?.substringBefore("@")
                    ?: user.phone
                    ?: "User"

                errorMessage = null

                onSuccess(
                    User(
                        id = user.id,
                        username = username,
                        email = user.email,
                        phone = user.phone
                    )
                )
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unable to sign in"
            } finally {
                isLoading = false
            }
        }
    }
}