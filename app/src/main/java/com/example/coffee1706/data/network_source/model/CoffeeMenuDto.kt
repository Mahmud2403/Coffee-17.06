package com.example.coffee1706.data.network_source.model

import androidx.annotation.Keep
import com.example.coffee1706.domain.data.CoffeeMenu
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CoffeeMenuDto(
	@Keep
	@SerializedName("id")
	val id: Int,
	@Keep
	@SerializedName("imageURL")
	val imageURL: String,
	@Keep
	@SerializedName("name")
	val name: String,
	@Keep
	@SerializedName("price")
	val price: Int
)

fun CoffeeMenu.toCoffeeMenu() = CoffeeMenuDto(
	id = cartList.id,
	imageURL = cartList.imageURL,
	name = cartList.name,
	price = cartList.price,
)