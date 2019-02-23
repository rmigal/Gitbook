package com.rusmyhal.gitbook

import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import com.rusmyhal.gitbook.model.entity.User


val testSearchUser = User("username", 12345L, "avatar")
val searchResponseSuccess = SearchResponse(listOf(testSearchUser))