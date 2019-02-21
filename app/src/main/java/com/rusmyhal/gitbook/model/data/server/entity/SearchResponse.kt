package com.rusmyhal.gitbook.model.data.server.entity

import com.google.gson.annotations.SerializedName
import com.rusmyhal.gitbook.model.entity.User


data class SearchResponse(@SerializedName("items") val users: List<User>)