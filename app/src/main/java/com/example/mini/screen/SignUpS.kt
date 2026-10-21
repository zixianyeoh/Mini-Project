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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini.model.User
import com.example.mini.ui.theme.CheckboxBackground
import com.example.mini.ui.theme.InputBackground
import com.example.mini.ui.theme.MainTextColor
import com.example.mini.ui.theme.MediumColor
import com.example.mini.ui.theme.OutlineColor
import com.example.mini.ui.theme.PageBackground
import com.example.mini.ui.theme.PrimaryColor
import com.example.mini.ui.theme.SecondaryTextColor
import com.example.mini.ui.theme.WeakColor
import com.example.mini.viewModel.SignUpVM

@Composable
fun SignUpS(
    viewModel: SignUpVM = viewModel(),
    onBack: () -> Unit,
    onGetCode: (String) -> Unit,
    onSignUp: (User) -> Unit,
    onTerms: () -> Unit,
    onPrivacy: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                text = "Sign Up",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainTextColor
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PageBackground)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 8.dp,
                bottom = 24.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ===== Header =====
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(PrimaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Create Account",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Create Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainTextColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Start your exclusive smart experience",
                        fontSize = 14.sp,
                        color = SecondaryTextColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Username", fontSize = 12.sp) },
                        placeholder = {
                            Text(
                                text = "Enter username",
                                color = OutlineColor,
                                fontSize = 15.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = SecondaryTextColor
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MainTextColor,
                            unfocusedTextColor = MainTextColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = InputBackground,
                            focusedBorderColor = PrimaryColor,
                            unfocusedBorderColor = InputBackground,
                            cursorColor = PrimaryColor,
                            focusedLabelColor = PrimaryColor,
                            unfocusedLabelColor = SecondaryTextColor
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = viewModel.phoneOrEmail,
                        onValueChange = { viewModel.onPhoneOrEmailChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Phone / Email", fontSize = 12.sp) },
                        placeholder = {
                            Text(
                                text = "Enter phone or email",
                                color = OutlineColor,
                                fontSize = 15.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                tint = SecondaryTextColor
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MainTextColor,
                            unfocusedTextColor = MainTextColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = InputBackground,
                            focusedBorderColor = PrimaryColor,
                            unfocusedBorderColor = InputBackground,
                            cursorColor = PrimaryColor,
                            focusedLabelColor = PrimaryColor,
                            unfocusedLabelColor = SecondaryTextColor
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))

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
                                    text = "6-digit code",
                                    fontSize = 14.sp,
                                    color = OutlineColor
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Security,
                                    contentDescription = null,
                                    tint = SecondaryTextColor
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MainTextColor,
                                unfocusedTextColor = MainTextColor,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = InputBackground,
                                focusedBorderColor = PrimaryColor,
                                unfocusedBorderColor = InputBackground,
                                cursorColor = PrimaryColor,
                                focusedLabelColor = PrimaryColor,
                                unfocusedLabelColor = SecondaryTextColor
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                viewModel.sendVerificationCode { contact ->
                                    onGetCode(contact)
                                }
                            },
                            enabled = viewModel.countdown == 0 && !viewModel.isLoading,
                            modifier = Modifier.height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CheckboxBackground,
                                contentColor = PrimaryColor,
                                disabledContainerColor = CheckboxBackground,
                                disabledContentColor = OutlineColor
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                text = when {
                                    viewModel.isLoading -> "Sending..."
                                    viewModel.countdown > 0 -> "${viewModel.countdown}s"
                                    else -> "Get Code"
                                },
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password", fontSize = 12.sp) },
                        placeholder = {
                            Text(
                                text = "8-20 characters",
                                fontSize = 14.sp,
                                color = OutlineColor
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = null,
                                tint = SecondaryTextColor
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                Icon(
                                    imageVector = if (viewModel.passwordVisibility)
                                        Icons.Outlined.Visibility
                                    else
                                        Icons.Outlined.VisibilityOff,
                                    contentDescription = if (viewModel.passwordVisibility)
                                        "Hide password" else "Show password",
                                    tint = OutlineColor
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (viewModel.passwordVisibility)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MainTextColor,
                            unfocusedTextColor = MainTextColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = InputBackground,
                            focusedBorderColor = PrimaryColor,
                            unfocusedBorderColor = InputBackground,
                            cursorColor = PrimaryColor,
                            focusedLabelColor = PrimaryColor,
                            unfocusedLabelColor = SecondaryTextColor
                        )
                    )

                    // 强度条
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(3) { index ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        if (viewModel.passwordStrength >= index + 1)
                                            when (viewModel.passwordStrength) {
                                                1 -> WeakColor
                                                2 -> MediumColor
                                                else -> PrimaryColor
                                            }
                                        else
                                            CheckboxBackground
                                    )
                            )
                        }
                    }
                    if (viewModel.password.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Password strength: ${viewModel.passwordStrengthLabel}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = when (viewModel.passwordStrength) {
                                1 -> WeakColor
                                2 -> MediumColor
                                3 -> PrimaryColor
                                else -> SecondaryTextColor
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = viewModel.confirmPassword,
                        onValueChange = { viewModel.onConfirmPasswordChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Confirm Password", fontSize = 12.sp) },
                        placeholder = {
                            Text(
                                text = "Re-enter password",
                                fontSize = 14.sp,
                                color = OutlineColor
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.VerifiedUser,
                                contentDescription = null,
                                tint = SecondaryTextColor
                            )
                        },
                        trailingIcon = {
                            if (viewModel.passwordsMatch) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = "Passwords match",
                                    tint = PrimaryColor
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MainTextColor,
                            unfocusedTextColor = MainTextColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = InputBackground,
                            focusedBorderColor = PrimaryColor,
                            unfocusedBorderColor = InputBackground,
                            cursorColor = PrimaryColor,
                            focusedLabelColor = PrimaryColor,
                            unfocusedLabelColor = SecondaryTextColor
                        )
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (viewModel.agreeToTerms) PrimaryColor else InputBackground
                                )
                                .clickable {
                                    viewModel.onAgreeToTermsChange(!viewModel.agreeToTerms)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (viewModel.agreeToTerms) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = buildAnnotatedString {
                                append("I have read and agree to ")
                                pushStringAnnotation("terms", "terms")
                                withStyle(
                                    SpanStyle(
                                        color = PrimaryColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append("Terms of Service") }
                                pop()
                                append(" & ")
                                pushStringAnnotation("privacy", "privacy")
                                withStyle(
                                    SpanStyle(
                                        color = PrimaryColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) { append("Privacy Policy") }
                                pop()
                            },
                            fontSize = 12.sp,
                            color = SecondaryTextColor,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { }
                        )
                    }
                    if (viewModel.errorMessage != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = viewModel.errorMessage!!,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.verifyCode(onSuccess = onSignUp)
                        },
                        enabled = !viewModel.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryColor,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
