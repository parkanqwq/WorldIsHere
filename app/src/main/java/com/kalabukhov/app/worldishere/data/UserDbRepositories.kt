package com.kalabukhov.app.worldishere.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalabukhov.app.worldishere.domain.entity.UserRepositoriesEntity

@Database(
    entities = [UserRepositoriesEntity::class],
    version = 1
)

abstract class UserDbRepositories : RoomDatabase() {
    abstract fun userRepositoriesDao() : UserRepositoriesDao
}