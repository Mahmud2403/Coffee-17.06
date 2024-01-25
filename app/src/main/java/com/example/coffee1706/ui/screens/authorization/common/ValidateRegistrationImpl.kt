package com.example.coffee1706.ui.screens.authorization.common

import com.example.coffee1706.utils.isEmail
import javax.inject.Inject

class ValidateRegistrationImpl @Inject constructor() : ValidateRegistration {

    override val emailHandler =
        fun(next: ValidateHandler<RegistrationParams, UpdateUserDataResult>) =
            fun(request: RegistrationParams): UpdateUserDataResult {
                val email = request.email.replace(" ", "")
                return when {
                    email.isEmpty() -> UpdateUserDataResult.EmailError("Адрес электронной почты пуст")
                    !email.isEmail() -> UpdateUserDataResult.EmailError("Адрес электронной почты введен неправильно")
                    else -> next(request)
                }
            }
    override val passwordHandler =
        fun(next: ValidateHandler<RegistrationParams, UpdateUserDataResult>) =
            fun(request: RegistrationParams): UpdateUserDataResult =
                when {
                    request.password.length < 8 -> UpdateUserDataResult.PasswordError(
                        "Пароль должен содержать не менее 8 букв"
                    )

                    request.password != request.confirmPassword -> UpdateUserDataResult.ConfirmPasswordError(
                        "пароль не совпадает"
                    )

                    else -> next(request)
                }
}


sealed interface UpdateUserDataResult {
    data class PasswordError(val message: String) : UpdateUserDataResult
    data class ConfirmPasswordError(val message: String) : UpdateUserDataResult
    data class EmailError(val message: String) : UpdateUserDataResult
    data object Success : UpdateUserDataResult
}

data class RegistrationParams(
    val password: String,
    val confirmPassword: String,
    val email: String
)


interface ValidateRegistration {
    val emailHandler: (ValidateHandler<RegistrationParams, UpdateUserDataResult>) -> (RegistrationParams) -> UpdateUserDataResult
    val passwordHandler: (ValidateHandler<RegistrationParams, UpdateUserDataResult>) -> (RegistrationParams) -> UpdateUserDataResult
}

typealias ValidateHandler<T, R> = (request: T) -> R
