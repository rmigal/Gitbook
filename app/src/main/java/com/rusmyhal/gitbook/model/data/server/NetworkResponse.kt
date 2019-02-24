package com.rusmyhal.gitbook.model.data.server


sealed class NetworkResponse<out T : Any>

class Success<T : Any>(val data: T) : NetworkResponse<T>()
class Error(val errorMessage: String?) : NetworkResponse<Nothing>()

