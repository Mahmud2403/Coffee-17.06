package com.example.coffee1706.data.network_source

import com.example.coffee1706.data.network_source.model.AuthorizationResponseDto
import com.example.coffee1706.data.network_source.model.CoffeeMenuDto
import com.example.coffee1706.data.network_source.model.LocationDto
import com.example.coffee1706.data.network_source.model.AuthorizationRequestDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface CoffeeApi {

	@POST("/auth/login")
	suspend fun userLogin(
		@Body authorizationRequestDto: AuthorizationRequestDto
	): AuthorizationResponseDto

	@POST("/auth/register")
	suspend fun userRegistration(
		@Body authorizationRequestDto: AuthorizationRequestDto
	): Response<AuthorizationResponseDto>

	@GET("/location/{id}/menu")
	suspend fun getCoffeeMenu(
		@Path("id") id: Int,
	): List<CoffeeMenuDto>

	@GET("locations")
	suspend fun getLocations(): List<LocationDto>

}