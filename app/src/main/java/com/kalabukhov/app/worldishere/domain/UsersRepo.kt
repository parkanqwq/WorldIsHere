package com.kalabukhov.app.worldishere.domain

import com.kalabukhov.app.worldishere.domain.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UsersRepo {
    val user: Observable<List<UserEntity>>
    fun put(user: UserEntity)
    fun clear()
}