package com.rusmyhal.gitbook.model.data.server

import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApi {

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Deferred<Response<SearchResponse>>
}