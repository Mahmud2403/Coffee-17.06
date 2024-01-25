package com.example.coffee1706.ui.screens.authorization.log_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffee1706.common.CoffeeButton
import com.example.coffee1706.common.CoffeeTopAppBar
import com.example.coffee1706.ui.screens.authorization.components.EmailAndPassword
import com.example.coffee1706.utils.clickableWithRipple


@Preview
@Composable
fun LogInScreenPreview() {
	LogInScreen(
		onEmailChange = {},
		onPasswordChange = {},
		onClickLogIn = {},
		onClickRegistration = {},
		uiState = LoginInUiState(),
		onClickMockData = {}
	)
}

@Composable
fun LogInScreen(
	modifier: Modifier = Modifier,
	onEmailChange: (String) -> Unit,
	onPasswordChange: (String) -> Unit,
	onClickLogIn: () -> Unit,
	onClickRegistration: () -> Unit,
	uiState: LoginInUiState,
	onClickMockData: () -> Unit,
) {
	Scaffold(
		modifier = modifier
			.fillMaxSize()
			.systemBarsPadding(),
		topBar = {
			CoffeeTopAppBar(
				title = "Вход",
				leadingIcon = {
					Icon(
						modifier = modifier
							.clickableWithRipple(
								onClick = onClickMockData,
							),
						imageVector = Icons.Default.Star,
						contentDescription = null
					)
				}
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
				email = uiState.email,
				password = uiState.password,
				onEmailChange = onEmailChange,
				onPasswordChange = onPasswordChange,
			)
			Spacer(modifier = Modifier.padding(top = 30.dp))
			CoffeeButton(
				onClick = onClickLogIn,
				text = "Войти",
			)
			Spacer(modifier = Modifier.padding(top = 16.dp))
			CoffeeButton(
				onClick = onClickRegistration,
				text = "Зарегистрироваться",
			)
		}
	}
}