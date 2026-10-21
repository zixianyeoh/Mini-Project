package com.example.mini.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini.ui.theme.InputBackground
import com.example.mini.ui.theme.MainTextColor
import com.example.mini.ui.theme.OutlineColor
import com.example.mini.ui.theme.PageBackground
import com.example.mini.ui.theme.PrimaryColor
import com.example.mini.ui.theme.SecondaryTextColor
import com.example.mini.viewModel.ForgotVM
import com.example.mini.viewModel.RecoveryMethod

@Composable
fun ForgotS(
    viewModel: ForgotVM = viewModel(),
    onBack: () -> Unit,
    onCodeVerified: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(PageBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(64.dp)
                .background(PageBackground)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = MainTextColor
                )
            }
            Text(
                text = "Forgot Password",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainTextColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp))
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(PrimaryColor, Color(0xFF6063EE)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Reset password",
                    tint = MainTextColor,
                    modifier = Modifier.size(44.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Reset Password",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MainTextColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your registered email or phone number to receive a verification code and recover your account.",
                fontSize = 14.sp,
                color = MainTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.92f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(InputBackground)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (viewModel.recoveryMethod == RecoveryMethod.EMAIL)
                                    Color.White
                                else
                                    InputBackground
                            )
                            .clickable {viewModel.selectMethod(RecoveryMethod.EMAIL)}
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "Email",
                            tint =
                                if (viewModel.recoveryMethod == RecoveryMethod.EMAIL)
                                    PrimaryColor
                                else
                                    SecondaryTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "Email",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color =
                                if (viewModel.recoveryMethod == RecoveryMethod.EMAIL)
                                    PrimaryColor
                                else
                                    SecondaryTextColor
                        )
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (viewModel.recoveryMethod == RecoveryMethod.PHONE)
                                    Color.White
                                else
                                    InputBackground
                            )
                            .clickable {viewModel.selectMethod(RecoveryMethod.PHONE)}
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhoneAndroid,
                            contentDescription = "Phone",
                            tint =
                                if (viewModel.recoveryMethod == RecoveryMethod.PHONE)
                                    PrimaryColor
                                else
                                    SecondaryTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "Phone",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color =
                                if (viewModel.recoveryMethod == RecoveryMethod.PHONE)
                                    PrimaryColor
                                else
                                    SecondaryTextColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text =
                        if (viewModel.recoveryMethod == RecoveryMethod.EMAIL)
                            "Enter your email address"
                        else
                            "Enter your phone number",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainTextColor
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = viewModel.account,
                    onValueChange = { viewModel.onAccountChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !viewModel.codeSent,
                    placeholder = {
                        Text(
                            text =
                                if (viewModel.recoveryMethod == RecoveryMethod.EMAIL)
                                    "name@example.com"
                                else
                                    "+60123456789",
                            color = OutlineColor,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector =
                                if (viewModel.recoveryMethod == RecoveryMethod.EMAIL)
                                    Icons.Outlined.Email
                                else
                                    Icons.Outlined.Smartphone,
                            contentDescription = "Email / Phone",
                            tint = OutlineColor
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MainTextColor,
                        unfocusedTextColor = MainTextColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = InputBackground,
                        focusedBorderColor = PrimaryColor,
                        unfocusedBorderColor = InputBackground,
                        cursorColor = PrimaryColor
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Verification Code",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainTextColor
                )
                Spacer(modifier= Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = viewModel.verificationCode,
                        onValueChange = { viewModel.onVerificationCodeChange(it) },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                text = "Enter code",
                                color = OutlineColor,
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Security,
                                contentDescription = "Verification code",
                                tint = OutlineColor
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MainTextColor,
                            unfocusedTextColor = MainTextColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = InputBackground,
                            focusedBorderColor = PrimaryColor,
                            unfocusedBorderColor = InputBackground,
                            cursorColor = PrimaryColor
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { viewModel.sendCode() },
                        enabled = !viewModel.isLoading && viewModel.countdown == 0,
                        modifier = Modifier.height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE2E7FF),
                            contentColor = PrimaryColor,
                            disabledContainerColor = Color(0xFFE2E7FF),
                            disabledContentColor = OutlineColor
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = when {
                                viewModel.isLoading -> "Sending"
                                viewModel.countdown > 0 -> "${viewModel.countdown}s"
                                viewModel.codeSent -> "Resend"
                                else -> "Get Code"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(InputBackground.copy(alpha = 0.7f))
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = PrimaryColor,
                        modifier = Modifier.padding(top = 2.dp).size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "We will send a temporary verification code. Enter the code to continue and choose a new password.",
                        fontSize = 12.sp,
                        color = SecondaryTextColor,
                        lineHeight = 18.sp
                    )
                }

                if (viewModel.infoMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.infoMessage!!,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {viewModel.verifyCode(onVerified = onCodeVerified)},
                    enabled = !viewModel.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                ) {
                    Text(
                        text = "Verify Code",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Outlined.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remember your password?",
                    fontSize = 14.sp,
                    color = SecondaryTextColor
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Sign In",
                    fontSize = 15.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(onClick = onBack)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}