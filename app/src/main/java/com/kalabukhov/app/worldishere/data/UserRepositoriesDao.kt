package com.kalabukhov.app.worldishere.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kalabukhov.app.worldishere.domain.entity.UserRepositoriesEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserRepositoriesDao {
    @Query("SELECT * FROM repositories")
    fun getGitHubUsersRepositories() : Observable<List<UserRepositoriesEntity>>

    @Query("DELETE FROM repositories")
    fun clear() : Completable

    @Insert
    fun put(user: UserRepositoriesEntity) : Completable

    @Delete
    fun delete(user: UserRepositoriesEntity) : Completable
}