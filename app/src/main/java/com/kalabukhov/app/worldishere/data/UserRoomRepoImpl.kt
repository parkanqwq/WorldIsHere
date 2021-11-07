package com.kalabukhov.app.worldishere.data

import com.kalabukhov.app.worldishere.domain.UsersRepo
import com.kalabukhov.app.worldishere.domain.entity.UserEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRoomRepoImpl(private val userDao: UserDao) : UsersRepo {
    override val user: Observable<List<UserEntity>> =
        userDao.getGitHubUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun put(user: UserEntity) {
        userDao.put(user)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun clear() {
        userDao.clear()
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}