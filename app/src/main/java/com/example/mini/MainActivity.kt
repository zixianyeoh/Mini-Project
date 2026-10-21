package com.example.mini

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mini.screen.ForgotS
import com.example.mini.screen.LoginS
import com.example.mini.screen.ResetPwdS
import com.example.mini.screen.SignUpS
import com.example.mini.ui.theme.MiniTheme

private enum class MiniPage {
    LOGIN,
    SIGN_UP,
    FORGOT_PASSWORD,
    RESET_PASSWORD
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniTheme {
                var currentPage by remember {
                    mutableStateOf(MiniPage.LOGIN)
                }

                when (currentPage) {
                    MiniPage.LOGIN -> LoginS(
                        onSignIn = { user ->
                            Toast.makeText(
                                this,
                                "Welcome ${user.username}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onForgotPwd = {
                            currentPage = MiniPage.FORGOT_PASSWORD
                        },
                        onSignUp = {
                            currentPage = MiniPage.SIGN_UP
                        }
                    )

                    MiniPage.SIGN_UP -> SignUpS(
                        onBack = {
                            currentPage = MiniPage.LOGIN
                        },
                        onGetCode = { contact ->
                            Toast.makeText(
                                this,
                                "Verification code requested for $contact",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onSignUp = { user ->
                            currentPage = MiniPage.LOGIN
                            Toast.makeText(
                                this,
                                "Account verified: ${user.username}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onTerms = {
                            Toast.makeText(
                                this,
                                "Terms of Service clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onPrivacy = {
                            Toast.makeText(
                                this,
                                "Privacy Policy clicked",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )

                    MiniPage.FORGOT_PASSWORD -> ForgotS(
                        onBack = {
                            currentPage = MiniPage.LOGIN
                        },
                        onCodeVerified = {
                            currentPage = MiniPage.RESET_PASSWORD
                        }
                    )

                    MiniPage.RESET_PASSWORD -> ResetPwdS(
                        onBack = {
                            currentPage = MiniPage.FORGOT_PASSWORD
                        },
                        onPwdUpdated = {
                            currentPage = MiniPage.LOGIN
                            Toast.makeText(
                                this,
                                "Password updated. Please sign in.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiniTheme {
    }
}

