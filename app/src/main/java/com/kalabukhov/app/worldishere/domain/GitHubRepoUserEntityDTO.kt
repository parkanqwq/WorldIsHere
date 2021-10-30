package com.kalabukhov.app.worldishere.domain

import com.squareup.moshi.Json

data class GitHubRepoUserEntityDTO (
    @field:Json(name = "avatar_url") val avatarUrl: String
)