package com.kalabukhov.app.worldishere.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kalabukhov.app.worldishere.domain.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getGitHubUsers() : Observable<List<UserEntity>>

    @Query("DELETE FROM users")
    fun clear() : Completable

    @Insert
    fun put(user: UserEntity) : Completable

    @Delete
    fun delete(user: UserEntity) : Completable
}