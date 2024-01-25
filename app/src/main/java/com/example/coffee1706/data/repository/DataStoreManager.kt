package com.example.coffee1706.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    fun userStateFlow() = dataStore.data.map {
        it[USER_STATE]
    }

    suspend fun setUserState(newState: UserState) {
        runCatching {
            when (newState) {
                is UserState.Authorized -> {
                    dataStore.edit {
                        it[USER_STATE] = UserState.Type.Authorized.name
                        it[USER_PASSWORD] = newState.password
                        it[ACCESS_TOKEN] = newState.token
                        it[USER_EMAIL] = newState.email
                    }
                }

                UserState.NotAuthorized -> {
                    clearData()
                }
            }
        }
    }

    fun getAccessToken() = dataStore.data.map {
        it[ACCESS_TOKEN]
    }

    private suspend fun clearData() = dataStore.edit {
        it[USER_STATE] = UserState.Type.NotAuthorized.name
        it.remove(USER_PASSWORD)
        it.remove(ACCESS_TOKEN)
        it.remove(USER_EMAIL)
    }
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")

        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_EMAIL = stringPreferencesKey("user_email")

        val USER_STATE = stringPreferencesKey("user_state")
    }
}
