package com.example.mini.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mini.model.User
import com.example.mini.ui.theme.InputBackground
import com.example.mini.ui.theme.MainTextColor
import com.example.mini.ui.theme.OutlineColor
import com.example.mini.ui.theme.PageBackground
import com.example.mini.ui.theme.PrimaryColor
import com.example.mini.ui.theme.SecondaryTextColor
import com.example.mini.viewModel.LoginVM


@Composable
fun LoginS(
    viewModel: LoginVM = viewModel(),
    onSignIn: (User) -> Unit,
    onForgotPwd: ()  -> Unit,
    onSignUp: () -> Unit
) {
    Column(
       modifier = Modifier
           .fillMaxSize()
           .background(PageBackground)
           .verticalScroll(rememberScrollState())
           .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(InputBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Logo",
                fontSize = 12.sp,
                color = OutlineColor,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MainTextColor,
            letterSpacing = (-0.5).sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your account details to sign in",
            fontSize = 14.sp,
            color = SecondaryTextColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            // Email / Phone Number
            OutlinedTextField(
                value = viewModel.emailOrPhone,
                onValueChange = { viewModel.onEmailOrPhoneChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email / Phone Number", fontSize = 13.sp) },
                placeholder = {
                    Text(
                        text = "Enter email / phone",
                        color = OutlineColor,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email / Phone",
                        tint = OutlineColor
                    )
                },
                trailingIcon = {
                    if (viewModel.emailOrPhone.isNotBlank()){
                        IconButton(
                            onClick = { viewModel.onEmailOrPhoneChange("") }
                        ){
                            Icon(
                                imageVector = Icons.Outlined.Cancel,
                                contentDescription = "Clear",
                                tint = OutlineColor
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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

            // Password
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPwdChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password", fontSize = 13.sp) },
                placeholder = {
                    Text(
                        text = "Enter 8-20 character",
                        color = OutlineColor,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Password",
                        tint = OutlineColor
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.togglePwdVisibility() }
                    ) {
                        Icon(
                            imageVector =
                                if (viewModel.passwordVisibility)
                                    Icons.Outlined.Visibility
                                else
                                    Icons.Outlined.VisibilityOff,
                            contentDescription = if (viewModel.passwordVisibility)
                                "Hide password"
                            else
                                "Show password",
                            tint = OutlineColor
                        )
                    }
                },
                singleLine = true,
                visualTransformation =
                    if (viewModel.passwordVisibility)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp,
                    color = PrimaryColor,
                    modifier = Modifier
                        .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onForgotPwd
                    )
                        .padding(vertical = 8.dp)
                )
            }
            if (viewModel.errorMessage != null) {
                Text(
                    text = viewModel.errorMessage!!,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.signIn(onSignIn)
                },
                enabled = !viewModel.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryTextColor
            )

            Text(
                text = " Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryColor,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onSignUp
                    )
                    .padding(vertical = 8.dp)
            )
        }
    }
}