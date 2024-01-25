package com.example.coffee1706.domain.use_case

import com.example.coffee1706.domain.repository.AuthorizationUserRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
	private val repo: AuthorizationUserRepository
) {
	suspend operator fun invoke(
		email: String,
		password: String
	) = repo.userRegistration(email = email, password = password)
}