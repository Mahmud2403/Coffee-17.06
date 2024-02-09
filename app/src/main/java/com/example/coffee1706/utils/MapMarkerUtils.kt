package com.example.coffee1706.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import com.example.coffee1706.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider

object MapMarkerUtils {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun MapObjectCollection.addPlaceMarkerWithListener(
        map: MapView,
        point: Point,
        context: Context,
//        image: Drawable?,
        image: Int,
        text: String,
        onClickMarker: () -> Unit,
    ): Pair<PlacemarkMapObject, MapObjectTapListener> {

        val placeMarker = addPlacemark(
            point,
            getPlaceMarkerView(
                image = image,
                text = text,
                context = context,
            )
        )
        val placeMarkerListener = MapObjectTapListener { _, _ ->
            onClickMarker()
//            vibrate(context)
            map.moveCamera(point)
            true
        }

        return Pair(placeMarker, placeMarkerListener)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun MapObjectCollection.addClusterMarkerWithListener(
        map: MapView,
        point: Point,
        context: Context,
        count: Int,
        onClickMarker: () -> Unit,
    ): Pair<PlacemarkMapObject, MapObjectTapListener> {

        val placeMarker = addPlacemark(
            point,
            getClusterMarkerView(
                count = count,
                context = context,
            )
        )
        val placeMarkerListener = MapObjectTapListener { _, _ ->
            onClickMarker()
//            vibrate(context)
            map.moveCamera(point)
            true
        }

        return Pair(placeMarker, placeMarkerListener)
    }

//    @SuppressLint("UseCompatLoadingForDrawables")
//    fun MapObjectCollection.addUserMarkerWithListener(
//        map: MapView,
//        point: Point,
//        onClickUser: () -> Unit,
//    ): Pair<PlacemarkMapObject, MapObjectTapListener> {
//
//        val placeMarker = addPlacemark(
//            point,
//            getUserMarkerView(
//                context = map.context,
//            )
//        )
//        val placeMarkerListener = MapObjectTapListener { _, _ ->
//            onClickUser()
////            vibrate(map.context)
//            map.moveCamera(point)
//            true
//        }
//
//        return Pair(placeMarker, placeMarkerListener)
//    }

    @SuppressLint("InflateParams")
    fun getPlaceMarkerView(
//        image: Drawable?,
        image: Int,
        context: Context,
        text: String,
    ) = LayoutInflater
        .from(context)
        .inflate(R.layout.place_marker, null, false)
        .apply {
            val placeImage =
                findViewById<ImageView>(R.id.imageView)
            val placeName =
                findViewById<TextView>(R.id.coffee_name)

//            placeImage.setImageDrawable(image)
            placeImage.setImageResource(image)
            placeName.text = text

        }
        .let(::ViewProvider)

    @SuppressLint("InflateParams")
    fun getClusterMarkerView(
        count: Int,
        context: Context,
    ) = LayoutInflater
        .from(context)
        .inflate(R.layout.cluster_marker, null, false)
        .apply {
            val textView =
                findViewById<TextView>(R.id.cluster_count)

            textView.text = count.toString()
        }
        .let(::ViewProvider)

//    @SuppressLint("InflateParams")
//    fun getUserMarkerView(
//        context: Context,
//    ) = LayoutInflater
//        .from(context)
//        .inflate(R.layout.user_marker, null, false)
//        .let(::ViewProvider)
}

fun MapView.moveCamera(
    point: Point,
    animation: Animation = Animation(Animation.Type.SMOOTH, (1f)),
    zoom: Float = 14f,
) {
    CameraPosition(point, zoom, (0f), (0f)).let {
        this.map.move(it, animation, null)
    }
}

fun com.example.coffee1706.data.network_source.model.Point.toMapPoint() = Point(
    latitude, longitude
)

//@Suppress("DEPRECATION")
//fun vibrate(
//    context: Context,
//    time: Long = 50,
//    amplitude: Int = 2
//) {
//    context.getSystemService<Vibrator>()?.let {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            it.vibrate(VibrationEffect.createOneShot(time, amplitude))
//        else it.vibrate(time)
//    }
//}