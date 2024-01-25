package com.example.coffee1706.ui.screens.maps.location_map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.coffee1706.R
import com.example.coffee1706.ui.screens.maps.LocationUiState
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun CoffeeLocationMapScreen(
	uiState: LocationUiState,
) {
	val mapView = rememberMapViewWithLifecycle()
	val context = LocalContext.current

	val imageProvider = ImageProvider.fromResource(context, R.drawable.ic_coffee_marker)

	ComposableLifecycle(
		onEvent = { _, event ->
			when (event) {
				Lifecycle.Event.ON_START -> {
					MapKitFactory.getInstance().onStart()
					mapView.onStart()
				}

				Lifecycle.Event.ON_STOP -> {
					mapView.onStop()
					MapKitFactory.getInstance().onStop()
				}

				else -> Unit
			}
		}
	)


	DisposableEffect(mapView) {
		val cameraListener =
			CameraListener { map, cameraPosition, cameraUpdateSource, finished ->
				if (finished) {

				}
			}

		mapView.mapWindow.map.addCameraListener(cameraListener)

		onDispose {
			mapView.mapWindow.map.removeCameraListener(cameraListener)
		}
	}

	Column(
		modifier = Modifier
			.fillMaxHeight()
			.fillMaxWidth()
			.background(Color.White)
	) {
		AndroidView({ mapView }) { mapView->
			CoroutineScope(Dispatchers.Main).launch {
				uiState.coffeeHouseList.forEach { location ->
					val placeMark = mapView.map.mapObjects.addPlacemark().apply {
						geometry = Point(location.point.latitude, location.point.longitude)
						setIcon(imageProvider)
					}
					mapView.map.move(
						CameraPosition(
							Point(location.point.latitude, location.point.longitude),
							/* zoom = */ 17.0f,
							/* azimuth = */ 150.0f,
							/* tilt = */ 30.0f
						)
					)
				}
			}
		}
	}
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
	val context = LocalContext.current
	val mapView = remember {
		MapView(context).apply {
			id = R.id.mapview
		}
	}
	return mapView
}

@Composable
fun ComposableLifecycle(
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {

	DisposableEffect(lifecycleOwner) {
		val observer = LifecycleEventObserver { source, event ->
			onEvent(source, event)
		}
		lifecycleOwner.lifecycle.addObserver(observer)

		onDispose {
			lifecycleOwner.lifecycle.removeObserver(observer)
		}
	}
}