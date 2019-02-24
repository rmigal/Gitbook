package com.rusmyhal.gitbook

import com.rusmyhal.gitbook.model.data.server.entity.Repository
import com.rusmyhal.gitbook.model.data.server.entity.SearchResponse
import com.rusmyhal.gitbook.model.data.server.entity.SearchUser
import com.rusmyhal.gitbook.model.data.server.entity.User


val testSearchUser =
    SearchUser("username", 12345L, "avatar")
val searchResponseSuccess = SearchResponse(listOf(testSearchUser))

val user = User("username", "avatar", "name", null, 2, 10)
val userWithCompany = User("username", "avatar", "name", "company", 2, 10)

val repositories = listOf(Repository(1000L, "name", "link", "language", 1000, 200))