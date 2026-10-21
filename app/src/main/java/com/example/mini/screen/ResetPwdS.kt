package com.example.mini.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.mini.viewModel.ResetPwdVM

@Composable
fun ResetPwdS(
    viewModel: ResetPwdVM = viewModel(),
    onBack: () -> Unit,
    onPwdUpdated: () -> Unit
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
                text = "Create New Password",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainTextColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(6.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(PrimaryColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Reset password",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Create New Password",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MainTextColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose a new password for your account.",
                fontSize = 14.sp,
                color = SecondaryTextColor,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.pwd,
                    onValueChange = { viewModel.onPwdChange(it) },
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
                        IconButton(onClick = { viewModel.togglePwdVisibility() }) {
                            Icon(
                                imageVector = if (viewModel.passwordVisible)
                                    Icons.Outlined.Visibility
                                else
                                    Icons.Outlined.VisibilityOff,
                                contentDescription = if (viewModel.passwordVisible)
                                    "Hide password" else "Show password",
                                tint = OutlineColor
                            )
                        }
                    },
                    singleLine = true,
                    visualTransformation = if (viewModel.passwordVisible)
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
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.confirmPwd,
                    onValueChange = { viewModel.onConfirmPwdChange(it) },
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
                        if (viewModel.pwdMatch) {
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

                if (viewModel.errorMessage != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = viewModel.errorMessage!!,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.updatePwd(onSuccess = onPwdUpdated) },
                    enabled = !viewModel.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Update Password",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}