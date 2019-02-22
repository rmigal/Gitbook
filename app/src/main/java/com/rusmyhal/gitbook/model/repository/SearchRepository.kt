package com.rusmyhal.gitbook.model.repository

import com.rusmyhal.gitbook.model.data.server.GithubApi
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response


open class SearchRepository(private val api: GithubApi) {

    fun searchUsers(query: String): Deferred<Response<SearchResponse>> = api.searchUsers(query)
}