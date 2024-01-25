package com.example.coffee1706.domain.use_case

import com.example.coffee1706.domain.repository.AuthorizationUserRepository
import javax.inject.Inject


class LogInUseCase @Inject constructor(
    private val repo: AuthorizationUserRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repo.userLogIn(email = email, password = password)
}