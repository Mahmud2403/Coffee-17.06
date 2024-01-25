package com.example.coffee1706.ui.screens.authorization.registration

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffee1706.data.network_source.collectAsResult
import com.example.coffee1706.domain.use_case.RegistrationUseCase
import com.example.coffee1706.ui.screens.authorization.common.RegistrationErrorHandler
import com.example.coffee1706.ui.screens.authorization.common.RegistrationParams
import com.example.coffee1706.ui.screens.authorization.common.UpdateUserDataResult
import com.example.coffee1706.ui.screens.authorization.common.ValidateRegistration
import com.example.coffee1706.utils.getErrorMessage
import com.example.coffee1706.utils.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private val registrationErrorHandler: RegistrationErrorHandler,
	private val registerUseCase: RegistrationUseCase,
	private val validate: ValidateRegistration,
) : ViewModel() {

	private var _registrationUiState = MutableStateFlow(RegistrationUiState())
	val registrationUiState = _registrationUiState.asStateFlow()

	private val resultChanel = Channel<Boolean>()
	val registerResult = resultChanel.receiveAsFlow()
		.flowOn(Dispatchers.Main.immediate)

	private fun validateEmail(email: String) {
		viewModelScope.launch {
			flow { emit(email.isEmail()) }
				.collectLatest {
					_registrationUiState.update { currentState ->
						currentState.copy(
							emailState = currentState.emailState.copy(
								valid = it
							)
						)
					}
				}
		}
	}

	fun validateData() {
		viewModelScope.launch {
			val params: RegistrationParams
			with(_registrationUiState.value.copy()) {
				params = RegistrationParams(
					password = password,
					confirmPassword = confirmPassword,
					email = emailState.value
				)
			}
			val chain: (RegistrationParams) -> UpdateUserDataResult
			with(validate) {
				chain = emailHandler(
					passwordHandler {
						UpdateUserDataResult.Success
					}
				)
			}
			with(_registrationUiState) {
				when (val result = chain(params)) {
					is UpdateUserDataResult.EmailError -> {
						update { currentState ->
							currentState.copy(
								emailState = currentState.emailState.copy(
									error = result.message
								)
							)
						}
					}

					is UpdateUserDataResult.PasswordError -> {
						update { currentState ->
							currentState.copy(
								passwordError = result.message
							)
						}
					}

					is UpdateUserDataResult.ConfirmPasswordError -> {
						update { currentState ->
							currentState.copy(
								confirmPasswordError = result.message
							)
						}
					}

					UpdateUserDataResult.Success -> {
						register()
					}
				}
			}
		}
	}


	fun changePassword(newValue: String) {
		_registrationUiState.update { currentState ->
			currentState.copy(
				password = newValue,
				passwordError = null
			)
		}
	}

	fun changeRepeatPassword(newValue: String) {
		_registrationUiState.update { currentState ->
			currentState.copy(
				confirmPassword = newValue,
				confirmPasswordError = null
			)
		}
	}

	fun changeEmail(newValue: String) {
		_registrationUiState.update { currentState ->
			currentState.copy(
				emailState = currentState.emailState.copy(
					value = newValue,
					error = null
				)
			)
		}
		validateEmail(newValue)
	}

//	private fun registrationUser() {
//		viewModelScope.launch {
//			registerUseCase(
//				email = registrationUiState.value.emailState.value,
//				password = registrationUiState.value.password,
//			).asResponseResult().collect { result ->
//
//				when (result) {
//					is Result.Error -> {
//						_registrationUiState.update { currentState ->
//							val errorMessage = runCatching {
//								if (result.exception == null) {
//									registrationErrorHandler.getErrorMessage(
//										result.responseBody.parseJson<ErrorResponse>()
//									)
//								} else result.exception.message
//							}.getOrDefault(result.exception?.message)
//
//							currentState.copy(
//								loading = false,
//								registrationError = errorMessage
//							)
//						}
//					}
//
//					Result.Loading -> {
//						_registrationUiState.update { currentState ->
//							currentState.copy(
//								loading = true,
//								registrationError = null
//							)
//						}
//					}
//
//					is Result.Success -> {
//						_registrationUiState.update { currentState ->
//							currentState.copy(
//								loading = false,
//								registrationError = null
//							)
//						}
//						resultChanel.send(true)
//					}
//				}
//			}
//		}
//	}

	suspend fun register() {
		val email = registrationUiState.value.emailState.value
		val password = registrationUiState.value.password
		return withContext(Dispatchers.IO) {
			registerUseCase(
				email = email,
				password = password,
			).collectAsResult(
				onSuccess = {
					resultChanel.send(true)
				},
				onError = { e, errorCode ->
					_registrationUiState.update { currentState ->
						currentState.copy(
							loading = false,
							registrationError = getErrorMessage(
								context = context,
								exception = e,
								exRes = errorCode,
							),
						)
					}
				},
				onLoading = {
					_registrationUiState.update { currentState ->
						currentState.copy(
							registrationError = null,
							loading = true,
						)
					}
				}
			)
		}
	}
}

data class RegistrationUiState(
	val emailState: EmailTextField = EmailTextField(),
	val password: String = "",
	val passwordError: String? = null,
	val confirmPassword: String = "",
	val confirmPasswordError: String? = null,
	val loading: Boolean = false,
	val registrationError: String? = null
)

data class EmailTextField(
	val value: String = "",
	val valid: Boolean = false,
	val error: String? = null
)