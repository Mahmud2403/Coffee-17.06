package com.example.coffee1706.ui.screens.authorization.registration

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffee1706.common.CoffeeButton
import com.example.coffee1706.common.CoffeeTextField
import com.example.coffee1706.common.CoffeeTopAppBar
import com.example.coffee1706.common.ErrorMessage
import com.example.coffee1706.ui.screens.authorization.components.EmailAndPassword
import com.example.coffee1706.utils.clickableWithRipple
import com.example.coffee1706.utils.toggle


@Preview
@Composable
fun RegistrationScreenPreview() {
	RegistrationScreen(
		onEmailChange = {},
		onPasswordChange = {},
		onConfirmPasswordChange = {},
		onClickRegistration = {},
		onClickLogIn = {},
		uiState = RegistrationUiState(),
	)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(
	modifier: Modifier = Modifier,
	onEmailChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
	onConfirmPasswordChange: (String) -> Unit,
	onClickRegistration: () -> Unit,
	onClickLogIn: () -> Unit,
	uiState: RegistrationUiState,
) {
	var showConfirmPassword by remember { mutableStateOf(false) }

	Scaffold(
		modifier = modifier
			.fillMaxSize()
			.systemBarsPadding(),
		topBar = {
			CoffeeTopAppBar(
				title = "Регистрация",
				
			)
		},
		containerColor = Color.White,
	) {
		Column(
			modifier = modifier
				.fillMaxSize()
				.padding(
					top = it.calculateTopPadding(),
					start = 16.dp,
					end = 16.dp,
				),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		) {
			EmailAndPassword(
				email = uiState.emailState.value,
				emailError = !uiState.emailState.error.isNullOrEmpty(),
				emailErrorMessage = uiState.emailState.error,
				password = uiState.password,
				passwordError = !uiState.passwordError.isNullOrEmpty(),
				passwordErrorMessage = uiState.passwordError,
				onEmailChange = onEmailChange,
				onPasswordChange = onPasswordChange,
			)
			Spacer(modifier = Modifier.padding(top = 24.dp))
			AnimatedVisibility(
				visible = !uiState.confirmPasswordError.isNullOrEmpty(),
			) {
				ErrorMessage(
					modifier = Modifier
						.padding(start = 16.dp, bottom = 8.dp),
					text = uiState.confirmPasswordError ?: ""
				)
			}
			CoffeeTextField(
				value = uiState.confirmPassword,
				onValueChange = onConfirmPasswordChange,
				title = "Повторите пароль",
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Password
				),
				visualTransformation = when (showConfirmPassword) {
					true -> VisualTransformation.None
					false -> PasswordVisualTransformation()
				},
				trailingIcon = {
					Icon(
						modifier = Modifier.clickableWithRipple(
							onClick = {
								showConfirmPassword = showConfirmPassword.toggle()
							}),
						imageVector = when (showConfirmPassword) {
							true -> Icons.Rounded.Visibility
							false -> Icons.Rounded.VisibilityOff
						},
						contentDescription = null,
						tint = Color(0xFFAF9479)
					)
				},
				placeholder = "confirm password",
			)
			Spacer(modifier = Modifier.padding(top = 30.dp))
			Text(
				modifier = Modifier.align(Alignment.CenterHorizontally),
				text = uiState.registrationError ?: "",
				style = MaterialTheme.typography.bodyLarge,
				color = Color.Red
			)
			CoffeeButton(
				onClick = onClickRegistration,
				text = "Регистрация",
			)
			Spacer(modifier = Modifier.padding(top = 16.dp))
			CoffeeButton(
				onClick = onClickLogIn,
				text = "Авторизоваться",
			)
		}
	}
}