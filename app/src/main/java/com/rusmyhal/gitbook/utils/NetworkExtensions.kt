package com.rusmyhal.gitbook.utils

import com.rusmyhal.gitbook.model.data.server.Error
import com.rusmyhal.gitbook.model.data.server.NetworkResponse
import com.rusmyhal.gitbook.model.data.server.Success
import kotlinx.coroutines.Deferred
import retrofit2.Response


suspend fun <T : Any> Deferred<Response<T>>.awaitResult(): NetworkResponse<T> {
    return try {
        val response = this.await()
        if (response.isSuccessful && response.body() != null) {
            Success(response.body()!!)
        } else {
            Error("${response.code()} ${response.message()}")
        }
    } catch (e: Exception) {
        Error(e.message)
    }
}