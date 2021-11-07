package com.kalabukhov.app.worldishere.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalabukhov.app.worldishere.domain.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)

abstract class UserDb : RoomDatabase() {
    abstract fun userDoa() : UserDao
}