package com.example.coffee1706.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.coffee1706.base.navigation.CoffeeNavHost
import com.yandex.mapkit.mapview.MapView


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScreen(
	modifier: Modifier = Modifier,
	appState: CoffeeAppState = rememberCoffeeAppState(),
) {
	Scaffold(
		modifier = modifier
			.navigationBarsPadding(),
	) {
		CoffeeNavHost(
			navController = appState.navController,
			onNavigateToDestination = appState::navigate,
			onClickBack = appState::onBackClick,
		)
	}
}

