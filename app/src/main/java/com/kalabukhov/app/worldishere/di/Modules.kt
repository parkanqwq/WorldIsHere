package com.kalabukhov.app.worldishere.di

import androidx.room.Room
import com.kalabukhov.app.worldishere.bus.EventBus
import com.kalabukhov.app.worldishere.data.UserDb
import com.kalabukhov.app.worldishere.data.UserDbRepositories
import com.kalabukhov.app.worldishere.data.UserRoomRepoImpl
import com.kalabukhov.app.worldishere.data.UserRoomRepoRepositoriesImpl
import com.kalabukhov.app.worldishere.domain.UsersRepo
import com.kalabukhov.app.worldishere.domain.UsersRepositoriesRepo
import com.kalabukhov.app.worldishere.rest.GitHubApi
import com.kalabukhov.app.worldishere.rest.GitHubApiRepo
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val dbModuleUsers = module {
    val dbPatch = "users.db"
    single { Room.databaseBuilder(get(), UserDb::class.java, dbPatch).build() }
    single { get<UserDb>().userDoa() }
    single<UsersRepo> { UserRoomRepoImpl(get()) }

    val dbPatchRepositories = "repositories.db"
    single { Room.databaseBuilder(get(), UserDbRepositories::class.java, dbPatchRepositories).build() }
    single { get<UserDbRepositories>().userRepositoriesDao() }
    single<UsersRepositoriesRepo> { UserRoomRepoRepositoriesImpl(get()) }
}

val retrofitModule = module {
    factory<GitHubApi> { Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build().create(GitHubApi::class.java) }

    factory<GitHubApiRepo> { Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build().create(GitHubApiRepo::class.java) }
}

val mvpModule = module {
    single { EventBus() }
}
