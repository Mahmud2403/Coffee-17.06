package com.example.coffee1706.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coffee1706.base.navigation.CoffeeNavigationDestination
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun rememberCoffeeAppState(
	navController: NavHostController = rememberNavController(),
	systemUiController: SystemUiController = rememberSystemUiController(),
): CoffeeAppState {
	return remember(navController, systemUiController) {
		CoffeeAppState(
			navController = navController,
			systemUiController = systemUiController,
		)
	}
}

@Stable
class CoffeeAppState constructor(
	val navController: NavHostController,
	val systemUiController: SystemUiController,
) {

	val currentDestination: NavDestination?
		@Composable get() = navController.currentBackStackEntryAsState().value?.destination

	fun navigate(destination: CoffeeNavigationDestination, route: String? = null) {
		navController.navigate(route ?: destination.route) {
			popUpTo(navController.graph.findStartDestination().id) { saveState = true }
			launchSingleTop = true
			restoreState = true
		}
	}

	fun onBackClick() {
		navController.popBackStack()
	}
}

fun SystemUiController.setDefaultSystemUiController() {
	setSystemBarsColor(
		color = Color.Transparent,
		darkIcons = true,
	)
	setNavigationBarColor(Color.Black)
}