package com.example.coffee1706.ui.screens.authorization.common

import com.example.coffee1706.domain.data.ErrorResponse
import java.util.Locale
import javax.inject.Inject

class RegistrationErrorHandler @Inject constructor() {
    fun getErrorMessage(response: ErrorResponse?): String {

        if (response == null) return "Unknown error"

        RegistrationField.values().forEach { registrationFiled ->
            response.items.errors[registrationFiled.name.toLowerCase(Locale.ROOT)]?.first()?.let { message ->
                return getErrorText(message, registrationFiled)
            }
        }
        return "Unknown error"
    }

    private fun getErrorText(errorCode: String, registrationField: RegistrationField): String {

        return when(registrationField){
            RegistrationField.Email -> {
                when(errorCode){
                    "validation.required" -> "поле электронной почты обязательно для заполнения"
                    // обработка других кодов ошибок
                    else -> "неизвестная ошибка в поле электронной почты"
                }
            }
            RegistrationField.Password -> {
                when(errorCode){
                    "validation.required" -> "поле пароль необходимо заполнить"
                    // обработка других кодов ошибок
                    else -> "ошибка в поле пароль неизвестна"
                }
            }
        }
    }
}

enum class RegistrationField {
    Email, Password
}