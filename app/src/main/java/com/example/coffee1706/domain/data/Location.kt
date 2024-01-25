package com.example.coffee1706.domain.data

import com.example.coffee1706.data.network_source.model.LocationDto
import com.example.coffee1706.data.network_source.model.Point
import kotlinx.serialization.Serializable

data class Location(
	val id: Int,
	val name: String,
	val point: Point
){
	companion object {
		val mock = Location(
			id = 1,
			name = "BEDOEV COFFEE",
			point = Point(
				44.095586,
				44.45345,
			)
		)
	}
}