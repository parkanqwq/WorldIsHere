package com.kalabukhov.app.worldishere.data

import com.kalabukhov.app.worldishere.domain.UsersRepositoriesRepo
import com.kalabukhov.app.worldishere.domain.entity.UserRepositoriesEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRoomRepoRepositoriesImpl(
    private val userRepositoriesDao: UserRepositoriesDao
    ) : UsersRepositoriesRepo {
    override val userRepositories: Observable<List<UserRepositoriesEntity>> =
        userRepositoriesDao.getGitHubUsersRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun put(user: UserRepositoriesEntity) {
        userRepositoriesDao.put(user)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun clear() {
        userRepositoriesDao.clear()
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}