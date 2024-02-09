package com.example.coffee1706.ui.screens.maps.location_map

import android.annotation.SuppressLint
import android.provider.SyncStateContract
import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.example.coffee1706.R
import com.example.coffee1706.common.CoffeeTopAppBar
import com.example.coffee1706.data.network_source.model.LocationDto
import com.example.coffee1706.domain.data.Location
import com.example.coffee1706.ui.screens.maps.LocationUiState
import com.example.coffee1706.ui.screens.maps.NearestCoffeeHouseViewModel
import com.example.coffee1706.ui.theme.BaseTextColor
import com.example.coffee1706.utils.ComposableLifecycle
import com.example.coffee1706.utils.MapMarkerUtils.addClusterMarkerWithListener
import com.example.coffee1706.utils.MapMarkerUtils.addPlaceMarkerWithListener
import com.example.coffee1706.utils.clickableWithRipple
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.util.concurrent.ConcurrentHashMap


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoffeeLocationMapScreen(
    uiState: LocationUiState,
    onClickBack: () -> Unit,
    viewModel: NearestCoffeeHouseViewModel = hiltViewModel()
) {
    val mapView = rememberMapView()
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
                val visibleRegion = mapView.mapWindow.map.visibleRegion
                val topLeft = visibleRegion.topLeft
                val topRight = visibleRegion.topRight
                val bottomRight = visibleRegion.bottomRight
                val bottomLeft = visibleRegion.bottomLeft
                val zoom = cameraPosition.zoom

                if (finished) {

                }
            }
        mapView.mapWindow.map.addCameraListener(cameraListener)
        onDispose {
            mapView.mapWindow.map.removeCameraListener(cameraListener)
        }
    }
    Scaffold(
        topBar = {
            CoffeeTopAppBar(
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickableWithRipple(
                                onClick = onClickBack
                            ),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = BaseTextColor,
                    )
                },
                title = "Карта",
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = it.calculateTopPadding())
        ) {

//            AndroidView({ mapView }) { mapView ->
//                CoroutineScope(Dispatchers.Main).launch {
//                    uiState.coffeeHouseList.forEach { location ->
//                        val mapObjects = mapView.mapWindow.map.mapObjects
//                        val placeMark = mapView.map.mapObjects.addPlacemark().apply {
//                            geometry = Point(42.98345709401506, 47.47029770539443)
//                            setIcon(imageProvider)
//                        }
//                        mapView.map.move(
//                            CameraPosition(
//                                Point(42.98345709401506, 47.47029770539443),
//                                /* zoom = */ 17.0f,
//                                /* azimuth = */ 150.0f,
//                                /* tilt = */ 30.0f
//                            )
//                        )
//                    }
//                }
//            }
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = {
                    val meetingCollection =
                        ConcurrentHashMap<com.example.coffee1706.data.network_source.model.Point, Pair<PlacemarkMapObject, MapObjectTapListener>>()

                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.mapPlaceStateFlow.collect() { locations ->
                            launch {
                                removeUnmatchedPlace(
                                    placeCollection = meetingCollection,
                                    mapObjects = mapView.mapWindow.map.mapObjects,
                                )
                            }.join()
                            launch {
                                locations.forEach { location ->
                                    addPlaceMarker(
                                        map = mapView,
                                        model = location,
                                        scope = this,
                                        image = R.drawable.ic_coffee_marker,
                                        text = location.name,
                                        cameraMapObject = meetingCollection,
                                        onClickMarker = { },

                                    )
                                    mapView.mapWindow.map.move(
                                        CameraPosition(Point(location.point.latitude, location.point.longitude),10.0f, 150.0f, 30.0f),
                                        Animation(Animation.Type.SMOOTH, 2f),
                                        null
                                    )
                                }
                            }
//                        uiState.coffeeHouseList.forEach { location ->
//                            launch(Dispatchers.Main) {
//                                removeUnmatchedPlace(
//                                    placeCollection = meetingCollection,
//                                    mapObjects = mapView.mapWindow.map.mapObjects,
//                                )
//                            }.join()
//                            launch(Dispatchers.IO) {
//
//                            }
                            updateMapCamera(mapView, Point(42.98345709401506, 47.47029770539443))
//                        }
                        }
                    }
                    mapView
                }
            )
        }
    }
}

fun removeUnmatchedPlace(
    placeCollection: ConcurrentHashMap<com.example.coffee1706.data.network_source.model.Point, Pair<PlacemarkMapObject, MapObjectTapListener>>,
    mapObjects: MapObjectCollection,
) {
    val keysToRemove = placeCollection
//        .filter { (key, _) -> !places.any { it.point == key } }
        .map { (key, value) ->
            mapObjects.remove(value.first)
            key
        }

    keysToRemove.forEach(placeCollection::remove)

}


@Composable
fun rememberMapView(
    initPosition: Point = Point(42.98345709401506, 47.47029770539443),
    initZoom: Float = 30.0f,
): MapView {
    val context = LocalContext.current

    return remember {
        LayoutInflater
            .from(context)
            .inflate(R.layout.map_view, null, false)
            .findViewById<MapView>(R.id.mapview)
            .apply {

//                mapWindow.map.move(
//                    CameraPosition(initPosition, initZoom, 150.0f, 30.0f),
//                    Animation(Animation.Type.SMOOTH, 2f),
//                    null
//                )
            }
    }
}

private fun updateMapCamera(map: MapView, point: Point) {
    map.mapWindow.map.move(
        CameraPosition(point, 15f, 0f, 0f),
        Animation(Animation.Type.SMOOTH, 2f),
        null
    )
}


suspend fun addPlaceMarker(
    map: MapView,
    image: Int,
    text: String,
    model: LocationDto,
    cameraMapObject: ConcurrentHashMap<com.example.coffee1706.data.network_source.model.Point, Pair<PlacemarkMapObject, MapObjectTapListener>>,
    onClickMarker: (LocationDto) -> Unit,
    scope: CoroutineScope,
) {

    val context = map.context


    scope.launch(Dispatchers.Main) {
        val (marker, markerListener) = map.mapWindow.map.mapObjects.addPlaceMarkerWithListener(
            map = map,
            point = with(model.point) { Point(latitude, longitude) },
            context = context,
            image = image,
            text = text,
            onClickMarker = {
                onClickMarker(model)
            }
        )
        cameraMapObject[model.point] = Pair(marker, markerListener)
            .apply {
                first.addTapListener(second)
            }
    }
}
