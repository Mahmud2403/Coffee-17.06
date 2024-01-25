package com.example.coffee1706.base.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.coffee1706.ui.screens.maps.navigation.CoffeeHouseListNavigation

object MainDestination : CoffeeNavigationDestination {
	override val route = "main_route"
	override val destination = "main_destination"
}


fun NavGraphBuilder.mainGraph(
	nestedGraphs: NavGraphBuilder.() -> Unit
) {
	navigation(
		route = MainDestination.route,
		startDestination = CoffeeHouseListNavigation.route
	){
		nestedGraphs()
	}
}