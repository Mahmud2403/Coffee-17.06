package com.example.coffee1706.ui.screens.menu.navigation

import android.content.Intent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.coffee1706.base.navigation.CoffeeNavigationDestination
import com.example.coffee1706.ui.screens.maps.navigation.CoffeeHouseListNavigation
import com.example.coffee1706.ui.screens.menu.CoffeeMenuScreen
import com.example.coffee1706.ui.screens.menu.CoffeeMenuViewModel
import com.example.coffee1706.ui.screens.pay.CoffeePayScreen
import com.example.coffee1706.ui.screens.pay.navigation.CoffeePayNavigation

object CoffeeMenuNavigation : CoffeeNavigationDestination {
	override val route = "menu_route/{id}"
	override val destination = "menu_destination"

	fun navigateWithArgument(id: Int) =
		"menu_route/$id"
}

fun NavGraphBuilder.coffeeMenu(
	navController: NavController,
	navigateTo: (destination: CoffeeNavigationDestination, route: String?) -> Unit,
	onClickBack: () -> Unit,
) {
	navigation(route = "Parent", startDestination = CoffeeMenuNavigation.route) {
		composable(
			route = CoffeeMenuNavigation.route,
			arguments = listOf(
				navArgument("id") { type = NavType.IntType },
			),
			deepLinks = listOf(
				navDeepLink {
					uriPattern = "http://147.78.66.203:3210/location/{id}/menu"
					action = Intent.ACTION_VIEW
				}
			),
		) { backStackEntry ->
			val viewModel = hiltViewModel<CoffeeMenuViewModel>()

			val id = requireNotNull(backStackEntry.arguments?.getInt("id"))

			LaunchedEffect(Unit) {
				viewModel.getCoffeeMenu(id)
			}
			CoffeeMenuScreen(
				onClickBack = onClickBack,
				onClickPayScreen = {
					navController.navigate(CoffeePayNavigation.route)
				},
			)
		}

		composable(
			route = CoffeePayNavigation.route,
		) { backStackEntry ->
			val parentEntry = remember(backStackEntry) {
				navController.getBackStackEntry(CoffeeMenuNavigation.route)
			}
			val viewModel = hiltViewModel<CoffeeMenuViewModel>(parentEntry)
			CoffeePayScreen(
				onClickBack = {
					navigateTo(CoffeeHouseListNavigation, null)
				},
				viewModel = viewModel
			)
		}
	}
}