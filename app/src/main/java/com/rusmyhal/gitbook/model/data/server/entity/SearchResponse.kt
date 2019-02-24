package com.rusmyhal.gitbook.model.data.server.entity

import com.google.gson.annotations.SerializedName


data class SearchResponse(@SerializedName("items") val users: List<SearchUser>)