package com.example.mini.viewModel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini.model.User
import com.example.mini.supabase.supabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.Phone
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put


class SignUpVM : ViewModel() {
    var username by mutableStateOf("")
        private set
    var phoneOrEmail by mutableStateOf("")
        private set
    var verificationCode by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set
    var passwordVisibility by mutableStateOf(false)
        private set
    var agreeToTerms by mutableStateOf(false)
        private set
    var countdown by mutableIntStateOf(0)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var codeSent by mutableStateOf(false)
        private set

    private var countdownJob: Job? = null
    private var codeSentContact: String? = null

    val passwordStrength: Int
        get() {
            if (password.isEmpty()) return 0
            var score = 0
            if (password.length >= 6)
                score++
            if (password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() })
                score++
            if (password.length >= 10 && password.any { !it.isLetterOrDigit() })
                score++
            return score
        }

    val passwordStrengthLabel: String
        get() = when (passwordStrength) {
            0 -> "None"
            1 -> "Weak"
            2 -> "Medium"
            else -> "Strong"
        }

    val passwordsMatch: Boolean
        get() = confirmPassword.isNotEmpty() && password == confirmPassword

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

    private fun normalizedContact(): String {
        val contact = phoneOrEmail.trim()
        return if (contact.contains("@")) {
            contact
        } else {
            normalizePhone(contact)
        }
    }

    private fun isValidContact(contact: String): Boolean {
        return if (contact.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(contact).matches()
        } else {
            Regex("^\\+[1-9]\\d{7,14}$").matches(contact)
        }
    }

    fun onUsernameChange(value: String) {
        if (!codeSent) {
            username = value
            errorMessage = null
        }
    }

    fun onPhoneOrEmailChange(value: String) {
        if (!codeSent) {
            phoneOrEmail = value
            errorMessage = null
        }
    }

    fun onVerificationCodeChange(value: String) {
        if (value.length <= 6 && value.all { it.isDigit() }) {
            verificationCode = value
            errorMessage = null
        }
    }

    fun onPasswordChange(value: String) {
        if (!codeSent) {
            password = value
            errorMessage = null
        }
    }

    fun onConfirmPasswordChange(value: String) {
        if (!codeSent) {
            confirmPassword = value
            errorMessage = null
        }
    }

    fun togglePasswordVisibility() {
        passwordVisibility = !passwordVisibility
    }
    fun onAgreeToTermsChange(checked: Boolean) {
        agreeToTerms = checked
        errorMessage = null
    }

    private fun validateBeforeCode(): Boolean {
        val contact = normalizedContact()
        errorMessage = when {
            username.isBlank() -> "Username is required."
            phoneOrEmail.isBlank() -> "Phone or email is required."
            !isValidContact(contact) -> "Invalid phone number or email."
            password.length !in 8..20 -> "Password must be 8-20 characters."
            confirmPassword.isBlank() -> "Confirm password is required."
            password != confirmPassword -> "Passwords do not match."
            !agreeToTerms -> "Please agree to the Terms  and Privacy Policy."
            else -> null
        }
        return errorMessage == null
    }

    fun sendVerificationCode(onCodeSent: (String) -> Unit) {
        if (isLoading || countdown > 0 || !validateBeforeCode()) return

        viewModelScope.launch {
            isLoading = true
            try {
                val contact = normalizedContact()
                val name = username.trim()
                val signupPwd = password

                if (!codeSent) {
                    if (contact.contains("@")) {
                        supabaseClient.client.auth.signUpWith(Email) {
                            email = contact
                            password = signupPwd

                            data = buildJsonObject {
                                put("username", name)
                            }
                        }
                    } else {
                        supabaseClient.client.auth.signUpWith(Phone) {
                            phone = contact
                            password = signupPwd

                            data = buildJsonObject {
                                put("username", name)

                            }
                        }
                    }
                    codeSentContact = contact
                    codeSent = true
                } else {
                    if (contact != codeSentContact) {
                        errorMessage = "Please use the original signup contact."
                        return@launch
                    }

                    if (contact.contains("@")) {
                        supabaseClient.client.auth.resendEmail(
                            OtpType.Email.SIGNUP, contact
                        )
                    } else {
                        supabaseClient.client.auth.resendPhone(
                            OtpType.Phone.SMS, contact
                        )
                    }
                }
                verificationCode = ""
                errorMessage = null
                startCountdown()
                onCodeSent(contact)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unable to send verification code"
            } finally {
                isLoading = false
            }
        }
    }

    private fun startCountdown() {
        countdown = 60
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
        }
    }

    fun validate(): Boolean {
        if (!validateBeforeCode()) return false
        errorMessage = when {
            !codeSent -> "Please request a verification code first."
            verificationCode.length != 6 -> "Invalid verification code."
            else -> null
        }
        return errorMessage == null
    }

    fun verifyCode(onSuccess: (User) -> Unit) {
        if (isLoading || !validate()) return

        viewModelScope.launch {
            isLoading = true

            try {
                val contact = normalizedContact()
                val code = verificationCode.trim()

                if (contact.contains("@")) {
                    supabaseClient.client.auth.verifyEmailOtp(
                        type = OtpType.Email.SIGNUP,
                        email = contact,
                        token = code
                    )
                } else {
                    supabaseClient.client.auth.verifyPhoneOtp(
                        type = OtpType.Phone.SMS,
                        phone = contact,
                        token = code
                    )
                }

                val user = supabaseClient.client.auth
                    .currentUserOrNull()
                    ?: error(
                        "Verification succeeded, but no user session was found."
                    )

                val savedUsername = user.userMetadata
                    ?.get("username")
                    ?.jsonPrimitive
                    ?.contentOrNull
                    ?: username.trim()

                errorMessage = null

                onSuccess(
                    User(
                        id = user.id,
                        username = savedUsername,
                        email = user.email,
                        phone = user.phone
                    )
                )

            } catch (e: Exception) {
                errorMessage = e.message ?: "Verification failed. Check your code."
            } finally {
                isLoading = false
            }
        }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }
}