package com.example.coffee1706.ui.screens.authorization.log_in

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffee1706.data.network_source.collectAsResult
import com.example.coffee1706.data.repository.UserSessionManager
import com.example.coffee1706.data.repository.UserState
import com.example.coffee1706.domain.use_case.LogInUseCase
import com.example.coffee1706.utils.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private val logInUseCase: LogInUseCase,
	private val userSessionManager: UserSessionManager,
) : ViewModel() {
	private var _loginInUiState = MutableStateFlow(LoginInUiState())
	val loginInUiState: StateFlow<LoginInUiState> = _loginInUiState.asStateFlow()

	private val resultChanel = Channel<Boolean>()
	val logInResult = resultChanel.receiveAsFlow()
		.flowOn(Dispatchers.Main.immediate)


	fun changeEmail(newValue: String) {
		_loginInUiState.update { currentState ->
			currentState.copy(
				email = newValue
			)
		}
	}

	fun changePassword(newValue: String) {
		_loginInUiState.update { currentState ->
			currentState.copy(
				password = newValue
			)
		}
	}

	fun loginIn() {
		val email = loginInUiState.value.email
		val password = loginInUiState.value.password
		viewModelScope.launch {
			logInUseCase(email, password)
				.collectAsResult(
					onSuccess = {
						userSessionManager.changeUserState(
							UserState.Authorized(
								token = it.token,
								tokenLifeTime = it.tokenLifetime,
								email = email,
								password = password,
							)
						)
						resultChanel.send(true)
					},
					onError = { e, errorCode ->
						_loginInUiState.update { currentState ->
							currentState.copy(
								loading = false,
								authorizationError = getErrorMessage(
									context = context,
									exception = e,
									exRes = errorCode,
								),
							)
						}
					},
					onLoading = {
						_loginInUiState.update { currentState ->
							currentState.copy(
								authorizationError = null,
								loading = true,
							)
						}
					}
				)
		}
	}

	fun mockData() {
		_loginInUiState.update { currentState ->
			currentState.copy(
				email = "mahmud@gmail.com",
				password = "12345678"
			)
		}
	}


}

data class LoginInUiState(
	val email: String = "",
	val password: String = "",
	val loading: Boolean = false,
	val authorizationError: String? = null
)