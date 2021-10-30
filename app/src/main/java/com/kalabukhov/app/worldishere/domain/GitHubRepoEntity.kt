package com.kalabukhov.app.worldishere.domain

import com.squareup.moshi.Json

data class GitHubRepoEntity(
    @field:Json(name = "login") val name: String?,
    @field:Json(name = "avatar_url") val avatarUrl: String?
)
