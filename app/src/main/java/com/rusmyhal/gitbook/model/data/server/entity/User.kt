package com.rusmyhal.gitbook.model.data.server.entity

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("name") val name: String,
    @SerializedName("company") val company: String?,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
) {

    val displayName: String
        get() = if (company != null) "$name, $company" else name

    var repositories: List<Repository> = emptyList()

    operator fun plus(newRepos: List<Repository>): User {
        repositories = newRepos
        return this
    }
}