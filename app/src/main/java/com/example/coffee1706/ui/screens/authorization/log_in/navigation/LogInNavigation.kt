package com.example.coffee1706.ui.screens.authorization.log_in.navigation

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
import androidx.navigation.navigation
import com.example.coffee1706.base.navigation.CoffeeNavigationDestination
import com.example.coffee1706.base.navigation.Direction
import com.example.coffee1706.base.navigation.currentRout
import com.example.coffee1706.base.navigation.slideIntoContainer
import com.example.coffee1706.base.navigation.slideOutOfContainer
import com.example.coffee1706.base.navigation.targetRout
import com.example.coffee1706.ui.screens.authorization.log_in.LogInScreen
import com.example.coffee1706.ui.screens.authorization.log_in.LogInViewModel
import com.example.coffee1706.ui.screens.authorization.registration.navigation.RegistrationNavigation
import com.example.coffee1706.ui.screens.maps.navigation.CoffeeHouseListNavigation

object AuthorizationNavigation : CoffeeNavigationDestination {
	override val route = "authorization_route"
	override val destination = "authorization_destination"
}

object LoginInNavigation : CoffeeNavigationDestination {
	override val route = "login_in_route"
	override val destination = "login_in_destination"
}

fun NavGraphBuilder.authorization(
	nestedGraphs: NavGraphBuilder.() -> Unit
) {
	navigation(
		route = AuthorizationNavigation.route,
		startDestination = LoginInNavigation.route
	){
		nestedGraphs()
	}
}

fun NavGraphBuilder.logIn(
	navigateTo: (destination: CoffeeNavigationDestination, route: String?) -> Unit,
	onClickBack: () -> Unit,
) {
	composable(
		route = LoginInNavigation.route,
		enterTransition = {
			when (currentRout()) {
				RegistrationNavigation.route -> slideIntoContainer(Direction.Left)
				else -> null
			}
		},
		exitTransition = {
			when (targetRout()) {
				RegistrationNavigation.route -> slideOutOfContainer(Direction.Right)
				else -> null
			}
		}

	) {
		val viewModel = hiltViewModel<LogInViewModel>()
		val uiState by viewModel.loginInUiState.collectAsStateWithLifecycle()
		val lifecycle = LocalLifecycleOwner.current.lifecycle
		val context = LocalContext.current

		LaunchedEffect(viewModel, lifecycle) {
			lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
				viewModel.logInResult.collect { isRegistered ->
					if (isRegistered) {
						Toast.makeText(context, "Авторизация прошла успешно", Toast.LENGTH_LONG)
							.show()
						navigateTo(
							CoffeeHouseListNavigation,
							null
						)
					}
				}
			}
		}

		LogInScreen(
			onEmailChange = viewModel::changeEmail,
			onPasswordChange = viewModel::changePassword,
			onClickLogIn = viewModel::loginIn,
			onClickRegistration = {
				navigateTo(RegistrationNavigation, null)
			},
			uiState = uiState,
			onClickMockData = viewModel::mockData,
		)
	}
}