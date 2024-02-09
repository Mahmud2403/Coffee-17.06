package com.example.coffee1706

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import com.example.coffee1706.base.AppScreen
import com.example.coffee1706.base.rememberCoffeeAppState
import com.example.coffee1706.base.setDefaultSystemUiController
import com.example.coffee1706.ui.theme.CoffeeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		MapKitFactory.initialize(this)




		setContent {
			val systemUiController = rememberSystemUiController()
			val appState = rememberCoffeeAppState(
				systemUiController = systemUiController,
			)

			CoffeeTheme {
				DisposableEffect(systemUiController) {
					appState.systemUiController.setDefaultSystemUiController()

					onDispose { }
				}
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					AppScreen(
						appState = appState,
					)
				}
			}
		}
	}
}