package com.example.coffee1706.ui.screens.authorization.common

import android.util.Patterns
import javax.inject.Inject


interface RegistrationValidation {
    fun validateData(
        email: String,
        password: String,
        repeatPassword: String
    ): ValidateResult

    fun validateEmail(
        email: String
    ): ValidateResult

    fun validatePassword(
        password: String,
        repeatPassword: String
    ): ValidateResult
}

class RegistrationValidationImpl @Inject constructor() : RegistrationValidation {
    override fun validateData(
        email: String,
        password: String,
        repeatPassword: String
    ): ValidateResult {

        validateEmail(email).let { if (it is ValidateResult.Error) return it }
        validatePassword(password, repeatPassword).let { if (it is ValidateResult.Error) return it }

        return ValidateResult.Success
    }

    override fun validateEmail(email: String) =
        when (Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            true -> ValidateResult.Success
            false -> ValidateResult.Error("Email does not match")
        }

    override fun validatePassword(password: String, repeatPassword: String): ValidateResult {
        return when {
            (password.length < 8) -> ValidateResult.Error("Password must contain at least 8 letters")
            (password.contains(" ")) -> ValidateResult.Error("Password must contain spaces")
            (password != repeatPassword) -> ValidateResult.Error("repeatPassword doesn't match")
            else -> ValidateResult.Success
        }
    }


}

sealed interface ValidateResult {
    data class Error(val message: String) : ValidateResult
    data object Success : ValidateResult
}