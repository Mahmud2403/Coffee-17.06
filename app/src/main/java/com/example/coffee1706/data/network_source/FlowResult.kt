package com.example.coffee1706.data.network_source

import com.example.coffee1706.R
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import okhttp3.ResponseBody
import retrofit2.Response

sealed interface Result<out T> {
	data class Success<T>(val data: T) : Result<T>

	data class Error(
		val exception: Throwable? = null,
		val responseBody: ResponseBody? = null,
		val errorMessageRes: Int = R.string.http_status_unknown_developer_message,
	) : Result<Nothing>

	data object Loading : Result<Nothing>
}

@Deprecated("use collectAsResult method")
fun <T> Flow<Response<T>>.asResponseResult(): Flow<Result<T>> {
	return this.map { response ->
		if (response.isSuccessful) {
			Result.Success(response.body()!!)
		} else {
			val errorBody = response.errorBody()
			Result.Error(
				responseBody = errorBody,
			)
		}
	}
		.onStart {
			emit(Result.Loading)
		}
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
	return this
		.map<T, Result<T>> { Result.Success(it) }
		.onStart { emit(Result.Loading) }
		.catch { emit(Result.Error(it)) }
}

suspend fun <T> Flow<T>.collectAsResult(
	onSuccess: suspend (T) -> Unit = {},
	onError: suspend (ex: Throwable, exRes: Int) -> Unit = { _, _ -> },
	onLoading: () -> Unit = {},
) {
	asResult().collect { result ->
		when (result) {
			is Result.Success -> onSuccess(result.data)
			is Result.Error -> {
				val errorMessageRes = when (result.exception) {
					else -> R.string.http_status_unknown_developer_message
				}
				onError(
					result.exception ?: UnknownError(),
					errorMessageRes,
				)
			}

			Result.Loading -> onLoading()
		}
	}
}


inline fun <reified T> ResponseBody?.parseJson(): T? {
	return this?.charStream()?.use {
		Gson().fromJson(it, T::class.java)
	}
}