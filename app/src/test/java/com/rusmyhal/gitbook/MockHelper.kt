package com.rusmyhal.gitbook

import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Response


fun <T> mockError(code: Int = 401, error: String): Response<T> {
    return Response.error(
        ResponseBody.create(MediaType.get("text/html"), "content"), okhttp3.Response.Builder()
            .code(code)
            .message(error)
            .protocol(Protocol.HTTP_1_1)
            .request(Request.Builder().url("http://localhost/").build())
            .build()
    )
}