package com.example.coffee1706.data.network_source.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class AuthorizationResponseDto(
	@Keep
	@SerializedName("tokenLifetime")
	val tokenLifetime: Long,
	@Keep
	@SerializedName("token")
	val token: String,
)