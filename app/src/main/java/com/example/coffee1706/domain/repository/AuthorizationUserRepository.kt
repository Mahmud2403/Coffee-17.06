package com.example.coffee1706.domain.repository

import com.example.coffee1706.data.network_source.model.AuthorizationResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthorizationUserRepository {
    suspend fun userRegistration(
        email: String,
        password: String,
    ): Flow<Response<AuthorizationResponseDto>>

    suspend fun userLogIn(
        email: String,
        password: String,
    ): Flow<AuthorizationResponseDto>
}