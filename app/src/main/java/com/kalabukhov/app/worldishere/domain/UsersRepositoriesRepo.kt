package com.kalabukhov.app.worldishere.domain

import com.kalabukhov.app.worldishere.domain.entity.UserEntity
import com.kalabukhov.app.worldishere.domain.entity.UserRepositoriesEntity
import io.reactivex.Observable
import io.reactivex.Single

interface UsersRepositoriesRepo {
    val userRepositories: Observable<List<UserRepositoriesEntity>>
    fun put(user: UserRepositoriesEntity)
    fun clear()
}