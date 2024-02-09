package com.example.coffee1706.ui.screens.maps.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.coffee1706.base.navigation.CoffeeNavigationDestination
import com.example.coffee1706.ui.screens.maps.location_list.NearestCoffeeHouseScreen
import com.example.coffee1706.ui.screens.maps.NearestCoffeeHouseViewModel
import com.example.coffee1706.ui.screens.maps.location_map.CoffeeLocationMapScreen
import com.example.coffee1706.ui.screens.menu.navigation.CoffeeMenuNavigation

object CoffeeHouseListNavigation : CoffeeNavigationDestination {
	override val route = "coffee_house_list_route"
	override val destination = "coffee_house_list__destination"
}

object CoffeeHouseMapsNavigation : CoffeeNavigationDestination {
	override val route = "coffee_house_maps_route"
	override val destination = "coffee_house_maps__destination"
}

fun NavGraphBuilder.nearestCoffee(
	navigateTo: (destination: CoffeeNavigationDestination, route: String?) -> Unit,
	onClickBack: () -> Unit,
) {
	composable(
		route = CoffeeHouseListNavigation.route,
	) {
		val viewModel = hiltViewModel<NearestCoffeeHouseViewModel>()
		val uiState by viewModel.locationUiState.collectAsStateWithLifecycle()
		NearestCoffeeHouseScreen(
			onClickMaps = {
				navigateTo(
					CoffeeHouseMapsNavigation,
					null
				)
			},
			onClickCoffee = { id ->
				navigateTo(
					CoffeeHouseListNavigation,
					CoffeeMenuNavigation.navigateWithArgument(id)
				)
			},
			onClickBack = onClickBack,
			uiState = uiState,
		)
	}

	composable(
		route = CoffeeHouseMapsNavigation.route,
	) {
		val viewModel = hiltViewModel<NearestCoffeeHouseViewModel>()
		val uiState by viewModel.locationUiState.collectAsStateWithLifecycle()

		CoffeeLocationMapScreen(
			uiState = uiState,
			onClickBack = onClickBack
		)
	}
}