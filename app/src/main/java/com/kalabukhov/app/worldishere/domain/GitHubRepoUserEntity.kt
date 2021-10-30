package com.kalabukhov.app.worldishere.domain

import com.squareup.moshi.Json

data class GitHubRepoUserEntity(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "size") val size: Int,
    @field:Json(name = "forks_count") val forksCount: Int,
    @field:Json(name = "owner") val owner: GitHubRepoUserEntityDTO
)