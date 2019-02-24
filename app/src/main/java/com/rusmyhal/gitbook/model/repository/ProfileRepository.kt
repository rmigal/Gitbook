package com.rusmyhal.gitbook.model.repository

import com.rusmyhal.gitbook.model.data.server.GithubApi


class ProfileRepository(private val api: GithubApi) {

    fun getUser(username: String) = api.getUser(username)

    fun getUserRepos(username: String) = api.getUserRepos(username)
}