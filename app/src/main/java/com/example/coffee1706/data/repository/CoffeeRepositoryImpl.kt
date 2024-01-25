package com.example.coffee1706.data.repository

import com.example.coffee1706.data.network_source.CoffeeApi
import com.example.coffee1706.data.network_source.model.CoffeeMenuDto
import com.example.coffee1706.data.network_source.model.LocationDto
import com.example.coffee1706.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
	private val api: CoffeeApi
): CoffeeRepository {
	override suspend fun getCoffeeMenu(id: Int): Flow<List<CoffeeMenuDto>> =
		flow {
			emit(
				api.getCoffeeMenu(
					id = id
				)
			)
		}

	override suspend fun getLocationCoffee(): Flow<List<LocationDto>> =
		flow {
			emit(
				api.getLocations()
			)
		}

}