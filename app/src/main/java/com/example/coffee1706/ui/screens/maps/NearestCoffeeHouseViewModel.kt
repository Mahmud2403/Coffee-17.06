package com.example.coffee1706.ui.screens.maps

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffee1706.data.network_source.collectAsResult
import com.example.coffee1706.data.network_source.model.LocationDto
import com.example.coffee1706.domain.data.Location
import com.example.coffee1706.domain.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearestCoffeeHouseViewModel @Inject constructor(
	private val coffeeRepository: CoffeeRepository,
): ViewModel() {
	private val _locationUiState = MutableStateFlow(LocationUiState())
	val locationUiState = _locationUiState.asStateFlow()

	init {
		getCoffeeHouse()
	}

	fun getLocation() {}

	private fun getCoffeeHouse() {
		viewModelScope.launch {
			coffeeRepository.getLocationCoffee().collectAsResult(
				onSuccess = {
					_locationUiState.update { currentState ->
						currentState.copy(
							coffeeHouseList = it,
						)
					}
				}
			)
		}
	}

}

data class LocationUiState(
	val coffeeHouseList: List<LocationDto> = emptyList()
)