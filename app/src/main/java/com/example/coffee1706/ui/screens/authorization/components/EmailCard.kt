package com.example.coffee1706.ui.screens.authorization.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffee1706.common.CoffeeTextField
import com.example.coffee1706.common.ErrorMessage
import com.example.coffee1706.utils.clickableWithRipple
import com.example.coffee1706.utils.toggle


@Preview
@Composable
fun LogInCardPreview() {
	EmailAndPassword(
		email = "",
		emailError = false,
		emailErrorMessage = "",
		password = "",
		passwordError = false,
		passwordErrorMessage = "",
		onEmailChange = {},
		onPasswordChange = {},
	)
}

@Composable
fun EmailAndPassword(
	email: String,
	emailError: Boolean = false,
	emailErrorMessage: String? = null,
	password: String,
	passwordError: Boolean = false,
	passwordErrorMessage: String? = null,
	onEmailChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
) {
	var showPassword by remember { mutableStateOf(false) }

	Column {
		AnimatedVisibility(
			visible = emailError,
		) {
			ErrorMessage(
				modifier = Modifier
					.padding(start = 16.dp, bottom = 8.dp),
				text = emailErrorMessage ?: ""
			)
		}
		CoffeeTextField(
			value = email,
			onValueChange = onEmailChange,
			title = "e-mail",
			placeholder = "example@example.ru",
		)
		Spacer(modifier = Modifier.padding(top = 24.dp))
		AnimatedVisibility(
			visible = passwordError,
		) {
			ErrorMessage(
				modifier = Modifier
					.padding(start = 16.dp, bottom = 8.dp),
				text = passwordErrorMessage ?: ""
			)
		}
		CoffeeTextField(
			value = password,
			onValueChange = onPasswordChange,
			title = "Пароль",
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Password
			),
			visualTransformation = when (showPassword) {
				true -> VisualTransformation.None
				false -> PasswordVisualTransformation()
			},
			trailingIcon = {
				Icon(
					modifier = Modifier.clickableWithRipple(
						onClick = {
							showPassword = showPassword.toggle()
						}),
					imageVector = when (showPassword) {
						true -> Icons.Rounded.Visibility
						false -> Icons.Rounded.VisibilityOff
					},
					contentDescription = null,
					tint = Color(0xFFAF9479)
				)
			},
			placeholder = "password",
		)
	}
}