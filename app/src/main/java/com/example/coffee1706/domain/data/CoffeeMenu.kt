package com.example.coffee1706.domain.data

import com.example.coffee1706.data.network_source.model.CoffeeMenuDto

data class CoffeeMenu(
	val cartList: CartList,
	var quantity: Int,
)

data class CartList(
	val id: Int,
	val imageURL: String,
	val name: String,
	val price: Int,
)

fun CoffeeMenuDto.toCart() = CartList(
	id,
	imageURL,
	name,
	price,
)