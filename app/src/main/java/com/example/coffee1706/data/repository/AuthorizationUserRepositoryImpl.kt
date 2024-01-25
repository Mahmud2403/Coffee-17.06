package com.example.coffee1706.data.repository

import com.example.coffee1706.data.network_source.CoffeeApi
import com.example.coffee1706.data.network_source.model.AuthorizationResponseDto
import com.example.coffee1706.data.network_source.model.AuthorizationRequestDto
import com.example.coffee1706.domain.repository.AuthorizationUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AuthorizationUserRepositoryImpl @Inject constructor(
    private val api: CoffeeApi
) : AuthorizationUserRepository {
    override suspend fun userRegistration(
        email: String,
        password: String,
    ): Flow<Response<AuthorizationResponseDto>> = flow {
        emit(api.userRegistration(AuthorizationRequestDto(login = email, password = password)))
    }

    override suspend fun userLogIn(
        email: String,
        password: String
    ): Flow<AuthorizationResponseDto> = flow {
        emit(api.userLogin(AuthorizationRequestDto(login = email, password = password)))
    }
}