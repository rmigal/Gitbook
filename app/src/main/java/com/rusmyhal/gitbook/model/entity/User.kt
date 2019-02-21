package com.rusmyhal.gitbook.model.entity

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("gravatar_url") val gravatarUrl: String?
)