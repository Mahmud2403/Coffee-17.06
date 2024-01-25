package com.example.coffee1706.data.network_source.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AuthorizationRequestDto(
	@Keep
	@SerializedName("login")
	val login: String,
	@Keep
	@SerializedName("password")
	val password: String,
)