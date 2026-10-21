package com.example.mini.viewModel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini.supabase.supabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class RecoveryMethod {
    EMAIL, PHONE
}
class ForgotVM : ViewModel() {
    var recoveryMethod by mutableStateOf(RecoveryMethod.EMAIL)
        private set
    var account by mutableStateOf("")
        private set
    var verificationCode by mutableStateOf("")
        private set
    var countdown by mutableIntStateOf(0)
        private set
    var codeSent by mutableStateOf(false)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var infoMessage by mutableStateOf<String?>(null)
        private set

    private var countdownJob: Job? = null
    private var requestedAccount: String? = null

    fun selectMethod(method: RecoveryMethod) {
        if (recoveryMethod != method) return

        recoveryMethod = method
        account = ""
        verificationCode = ""
        codeSent = false
        requestedAccount = null
        errorMessage = null
        infoMessage = null
        stopCountdown()
    }

    fun onAccountChange(value: String) {
        if (!codeSent) {
            account = value
            errorMessage = null
            infoMessage = null
        }
    }

    fun onVerificationCodeChange(value: String) {
        if (value.length <= 6 && value.all { it.isDigit() }) {
            verificationCode = value
            errorMessage = null
        }
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

    private fun normalizedAccount(): String =
        if (recoveryMethod == RecoveryMethod.EMAIL)
            account.trim()
        else
            normalizePhone(account.trim())

    private fun validateAccount(): Boolean {
        val target = normalizedAccount()

        errorMessage = when {
            target.isBlank() -> "Please enter email or phone number"
            recoveryMethod == RecoveryMethod.EMAIL &&
                    !Patterns.EMAIL_ADDRESS.matcher(target).matches()
                        -> "Please enter a valid email address"
            recoveryMethod == RecoveryMethod.PHONE &&
                    !Regex("^\\+[1-9]\\d{7,14}$").matches(target)
                        -> "Please enter a valid phone number"
            else -> null
        }
        return errorMessage == null
    }

    fun sendCode(){
        if (isLoading || countdown > 0 || !validateAccount()) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            infoMessage = null

            try {
                val target = normalizedAccount()

                when (recoveryMethod) {
                    RecoveryMethod.EMAIL -> {
                        supabaseClient.client.auth.resetPasswordForEmail( email = target)
                    }
                    RecoveryMethod.PHONE -> {
                        supabaseClient.client.auth.signUpWith(OTP) {
                            phone = target
                            createUser = false
                        }
                    }
                }
                requestedAccount = target
                codeSent = true
                verificationCode = ""
                infoMessage  = "If this account exists, a verification code will be sent."
                startCountdown()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unable to send verification code"
            } finally {
                isLoading = false
            }
        }
    }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdown = 60
        countdownJob = viewModelScope.launch {
            while (countdown > 0) {
                delay(1000)
                countdown--
            }
        }
    }

    private fun stopCountdown() {
        countdownJob?.cancel()
        countdownJob = null
        countdown = 0
    }

    fun verifyCode(onVerified: () -> Unit) {
        if (isLoading) return

        if (!codeSent || requestedAccount != normalizedAccount()) {
            errorMessage = "Please request new verification code first"
            return
        }

        if (verificationCode.length != 6) {
            errorMessage = "Please enter 6-digit verification code"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            infoMessage = null

            try {
                val target = normalizedAccount()
                val token = verificationCode.trim()

                when (recoveryMethod) {
                    RecoveryMethod.EMAIL -> {
                        supabaseClient.client.auth.verifyEmailOtp(
                            type = OtpType.Email.RECOVERY,
                            email = target,
                            token = token
                        )
                    }

                    RecoveryMethod.PHONE -> {
                        supabaseClient.client.auth.verifyPhoneOtp(
                            type = OtpType.Phone.SMS,
                            phone = target,
                            token = token
                        )
                    }
                }

                onVerified()

            } catch (e: Exception) {
                errorMessage = e.message
                    ?: "Verification failed. Check the code and try again."
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