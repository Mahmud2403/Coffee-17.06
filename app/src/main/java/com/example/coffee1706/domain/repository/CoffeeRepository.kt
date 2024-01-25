package com.example.coffee1706.domain.repository

import com.example.coffee1706.data.network_source.model.CoffeeMenuDto
import com.example.coffee1706.data.network_source.model.LocationDto
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {

	suspend fun getCoffeeMenu(id: Int): Flow<List<CoffeeMenuDto>>

	suspend fun getLocationCoffee(): Flow<List<LocationDto>>
}