package com.rusmyhal.gitbook.model.data.server.entity

import com.google.gson.annotations.SerializedName


data class SearchUser(
    @SerializedName("login") val username: String,
    @SerializedName("id") val id: Long,
    @SerializedName("avatar_url") val avatarUrl: String?
)