package com.example.coffee1706.utils

import android.content.Context
import android.util.Log
import com.example.coffee1706.data.network_source.parseJson
import com.example.coffee1706.domain.data.ErrorResponse
import retrofit2.HttpException

fun getErrorMessage(context: Context, exception: Throwable, exRes: Int): String {
	Log.e("getErrorMessage", "exception - $exception")
	return runCatching {
		if (exception is HttpException) {
			exception.response()?.errorBody()
				?.parseJson<ErrorResponse>()?.items?.errors?.values?.joinToString(", ")
		} else null
	}.getOrNull() ?: run {
		context.resources.getString(exRes)
	}
}