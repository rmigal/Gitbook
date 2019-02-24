package com.rusmyhal.gitbook.model.data.server.entity

import com.google.gson.annotations.SerializedName


data class Repository(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val link: String,
    @SerializedName("language") val language: String?,
    @SerializedName("forks_count") val forksCount: Long,
    @SerializedName("stargazers_count") val starsCount: Long
)