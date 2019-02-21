package com.rusmyhal.gitbook.model.repository

import com.rusmyhal.gitbook.model.data.server.GithubApi
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import kotlinx.coroutines.Deferred


class SearchRepository(private val api: GithubApi) {

    fun searchUsers(query: String): Deferred<SearchResponse> = api.searchUsers(query)
}