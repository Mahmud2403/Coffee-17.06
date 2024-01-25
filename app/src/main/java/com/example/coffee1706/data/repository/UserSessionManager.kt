package com.example.coffee1706.data.repository

import androidx.annotation.Keep
import androidx.compose.runtime.compositionLocalOf
import com.example.coffee1706.data.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionManager @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
) {
    private val ioCoroutineScope = CoroutineScope(ioDispatcher)

    @Volatile
    private var actualAccessToken: String? = null

    val userState = dataStoreManager.userStateFlow()
        .map(::mapToUserState)
        .stateIn(ioCoroutineScope, SharingStarted.Eagerly, UserState.Type.Undefined)

    fun userIsAuthorize(): Boolean {
        return userState.value == UserState.Type.Authorized
    }

    suspend fun getAccessToken(): String? {
        return actualAccessToken ?: dataStoreManager.getAccessToken().first()
    }

    suspend fun changeUserState(newState: UserState) {
        return dataStoreManager.setUserState(newState)
    }

    private fun mapToUserState(state: String?): UserState.Type {
        return if (state.isNullOrBlank()) {
            UserState.Type.NotAuthorized
        } else {
            runCatching {
                UserState.Type.valueOf(state)
            }.getOrDefault(UserState.Type.NotAuthorized)
        }
    }
}

@Keep
sealed interface UserState {
    @Keep
    data class Authorized(
        val token: String,
        val tokenLifeTime: Long,
        val password: String,
        val email: String,
    ) : UserState

    @Keep
    object NotAuthorized : UserState

    enum class Type {
        Authorized, NotAuthorized, Undefined
    }
}

val LocaleUserState = compositionLocalOf { UserState.Type.Undefined }