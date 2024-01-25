package com.example.coffee1706.ui.screens.pay.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.coffee1706.base.navigation.CoffeeNavigationDestination
import com.example.coffee1706.ui.screens.menu.CoffeeMenuViewModel
import com.example.coffee1706.ui.screens.menu.navigation.CoffeeMenuNavigation
import com.example.coffee1706.ui.screens.pay.CoffeePayScreen

object CoffeePayNavigation : CoffeeNavigationDestination {
	override val route = "coffee_pay_route"
	override val destination = "coffee_pay_destination"
}

