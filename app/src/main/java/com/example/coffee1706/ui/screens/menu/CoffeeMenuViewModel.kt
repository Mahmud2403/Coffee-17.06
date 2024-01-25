package com.example.coffee1706.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffee1706.data.network_source.collectAsResult
import com.example.coffee1706.data.network_source.model.CoffeeMenuDto
import com.example.coffee1706.domain.data.CoffeeMenu
import com.example.coffee1706.domain.data.toCart
import com.example.coffee1706.domain.repository.CoffeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeMenuViewModel @Inject constructor(
	private val coffeeRepository: CoffeeRepository,
) : ViewModel() {

	private val _menuUiState = MutableStateFlow(CoffeeMenuUiState())
	val menuUiState = _menuUiState.asStateFlow()


	fun addToCart(coffeeMenu: CoffeeMenuDto, quantity: Int) {
		_menuUiState.update { currentState ->
			currentState.copy(
				cartList = currentState.cartList.toMutableList().apply {
					val existingItem = find { it.cartList.id == coffeeMenu.id }
					if (existingItem != null) {
						existingItem.quantity += quantity
					} else {
						add(CoffeeMenu(cartList = coffeeMenu.toCart(), quantity = quantity))
					}
				}
			)
		}
	}

	fun removeFromCart(coffeeMenu: CoffeeMenuDto, quantity: Int) {
		_menuUiState.update { currentState ->
			currentState.copy(
				cartList = currentState.cartList.toMutableList().apply {
					val existingItem = find { it.cartList.id == coffeeMenu.id }
					if (existingItem != null) {
						existingItem.quantity -= quantity
						if (existingItem.quantity <= 0) {
							remove(existingItem)
						}
					}
				}
			)
		}
	}



	fun getCoffeeMenu(id: Int) {
		viewModelScope.launch {
			coffeeRepository.getCoffeeMenu(id).collectAsResult(
				onSuccess = {
					_menuUiState.update { currentState ->
						currentState.copy(
							menuList = it
						)
					}
				}
			)
		}
	}
}

data class CoffeeMenuUiState(
	val menuList: List<CoffeeMenuDto> = emptyList(),
	val cartList: List<CoffeeMenu> = emptyList(),
)

