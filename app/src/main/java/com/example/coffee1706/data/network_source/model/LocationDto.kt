package com.example.coffee1706.data.network_source.model


import androidx.annotation.Keep
import com.example.coffee1706.domain.data.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LocationDto(
    @Keep
    @SerialName("id")
    val id: Int,
    @Keep
    @SerialName("name")
    val name: String,
    @Keep
    @SerialName("point")
    val point: Point
)
@Keep
@Serializable
data class Point(
    @Keep
    @SerialName("latitude")
    val latitude: Double,
    @Keep
    @SerialName("longitude")
    val longitude: Double
)

fun LocationDto.toLocation() = Location(
    id = id,
    name = name,
    point = point,
)