package com.rusmyhal.gitbook.model.data.server

import com.rusmyhal.gitbook.model.data.server.entity.Repository
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import com.rusmyhal.gitbook.model.data.server.entity.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApi {

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Deferred<Response<SearchResponse>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Deferred<Response<User>>

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Deferred<Response<List<Repository>>>
}