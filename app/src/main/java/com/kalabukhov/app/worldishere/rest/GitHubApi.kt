package com.kalabukhov.app.worldishere.rest

import com.kalabukhov.app.worldishere.domain.GitHubRepoEntity
import com.kalabukhov.app.worldishere.domain.GitHubRepoUserEntity
import io.reactivex.Single
import retrofit2.http.*

interface GitHubApi {
    @GET("users/{username}")
    fun listRepo(
        @Path("username") username: String
    ) : Single<GitHubRepoEntity>
}

interface GitHubApiRepo {
    @GET("users/{username}/repos")
    fun listRepo(
        @Path("username") username: String
    ) : Single<List<GitHubRepoUserEntity>>
}