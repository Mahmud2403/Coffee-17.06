package com.example.coffee1706.ui.screens.authorization.registration.navigation

import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.coffee1706.base.navigation.CoffeeNavigationDestination
import com.example.coffee1706.base.navigation.Direction
import com.example.coffee1706.base.navigation.currentRout
import com.example.coffee1706.base.navigation.slideIntoContainer
import com.example.coffee1706.base.navigation.slideOutOfContainer
import com.example.coffee1706.base.navigation.targetRout
import com.example.coffee1706.ui.screens.authorization.log_in.navigation.LoginInNavigation
import com.example.coffee1706.ui.screens.authorization.registration.RegistrationScreen
import com.example.coffee1706.ui.screens.authorization.registration.RegistrationViewModel

object RegistrationNavigation : CoffeeNavigationDestination {
	override val route = "registration_route"
	override val destination = "registration_destination"
}

fun NavGraphBuilder.registration(
	navigateTo: (destination: CoffeeNavigationDestination, route: String?) -> Unit,
	onClickBack: () -> Unit,
) {
	composable(
		route = RegistrationNavigation.route,
		enterTransition = {
			when (currentRout()) {
				LoginInNavigation.route -> slideIntoContainer(Direction.Left)
				else -> null
			}
		},
		exitTransition = {
			when (targetRout()) {
				LoginInNavigation.route -> slideOutOfContainer(Direction.Right)
				else -> null
			}
		}
	) {
		val viewModel = hiltViewModel<RegistrationViewModel>()
		val uiState by viewModel.registrationUiState.collectAsStateWithLifecycle()
		val lifecycle = LocalLifecycleOwner.current.lifecycle
		val context = LocalContext.current

		LaunchedEffect(viewModel, lifecycle) {
			lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
				viewModel.registerResult.collect { isRegistered ->
					if (isRegistered) {
						Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_LONG)
							.show()
						onClickBack()
					}
				}
			}
		}

		RegistrationScreen(
			onEmailChange = viewModel::changeEmail,
			onPasswordChange = viewModel::changePassword,
			onConfirmPasswordChange = viewModel::changeRepeatPassword,
			onClickRegistration = viewModel::validateData,
			onClickLogIn = {
				navigateTo(LoginInNavigation, null)
			},
			uiState = uiState,
		)
	}
}