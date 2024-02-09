package com.example.coffee1706.base.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.coffee1706.data.repository.LocaleUserState
import com.example.coffee1706.data.repository.UserState
import com.example.coffee1706.ui.screens.authorization.log_in.navigation.LoginInNavigation
import com.example.coffee1706.ui.screens.authorization.log_in.navigation.authorization
import com.example.coffee1706.ui.screens.authorization.log_in.navigation.logIn
import com.example.coffee1706.ui.screens.authorization.registration.navigation.registration
import com.example.coffee1706.ui.screens.maps.navigation.CoffeeHouseListNavigation
import com.example.coffee1706.ui.screens.maps.navigation.nearestCoffee
import com.example.coffee1706.ui.screens.menu.navigation.coffeeMenu

@Composable
fun CoffeeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToDestination: (destination: CoffeeNavigationDestination, route: String?) -> Unit,
    onClickBack: () -> Unit,
) {
    val userType = LocaleUserState.current


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination(type = userType),
    ) {
        logIn(
            navigateTo = onNavigateToDestination,
            onClickBack = onClickBack,
        )
        registration(
            navigateTo = onNavigateToDestination,
            onClickBack = onClickBack,
        )


        nearestCoffee(
            navigateTo = onNavigateToDestination,
            onClickBack = onClickBack,
        )
        coffeeMenu(
            navigateTo = onNavigateToDestination,
            onClickBack = onClickBack,
            navController = navController
        )
    }
}

@Composable
private fun startDestination(type: UserState.Type) =
    when (type) {
        UserState.Type.Authorized -> CoffeeHouseListNavigation.route

        else -> LoginInNavigation.route
    }

const val DURATION_NAVIGATION_ANIMATION = 250

fun AnimatedContentTransitionScope<NavBackStackEntry>.currentRout() = initialState.destination.route

fun AnimatedContentTransitionScope<NavBackStackEntry>.targetRout() = targetState.destination.route

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideIntoContainer(direction: Direction): EnterTransition {
    val slideDirection = when (direction) {
        Direction.Right -> AnimatedContentTransitionScope.SlideDirection.Right
        Direction.Left -> AnimatedContentTransitionScope.SlideDirection.Left
        Direction.Up -> AnimatedContentTransitionScope.SlideDirection.Up
        Direction.Down -> AnimatedContentTransitionScope.SlideDirection.Down
    }
    return slideIntoContainer(
        slideDirection,
        animationSpec = tween(
            durationMillis = DURATION_NAVIGATION_ANIMATION,
            easing = EaseInOutQuad
        )
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutOfContainer(direction: Direction): ExitTransition {
    val slideDirection = when (direction) {
        Direction.Right -> AnimatedContentTransitionScope.SlideDirection.Right
        Direction.Left -> AnimatedContentTransitionScope.SlideDirection.Left
        Direction.Up -> AnimatedContentTransitionScope.SlideDirection.Up
        Direction.Down -> AnimatedContentTransitionScope.SlideDirection.Down
    }
    return slideOutOfContainer(
        slideDirection,
        animationSpec = tween(
            durationMillis = DURATION_NAVIGATION_ANIMATION,
            easing = EaseInOutQuad
        )
    )
}

enum class Direction {
    Right, Left, Up, Down
}