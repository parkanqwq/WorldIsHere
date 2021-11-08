package com.kalabukhov.app.worldishere.di

import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.kalabukhov.app.worldishere.App
import com.kalabukhov.app.worldishere.Screens
import com.kalabukhov.app.worldishere.app
import com.kalabukhov.app.worldishere.bus.EventBus
import com.kalabukhov.app.worldishere.data.UserDb
import com.kalabukhov.app.worldishere.data.UserDbRepositories
import com.kalabukhov.app.worldishere.data.UserRoomRepoImpl
import com.kalabukhov.app.worldishere.data.UserRoomRepoRepositoriesImpl
import com.kalabukhov.app.worldishere.domain.GitHubRepoEntity
import com.kalabukhov.app.worldishere.domain.UsersRepo
import com.kalabukhov.app.worldishere.domain.UsersRepositoriesRepo
import com.kalabukhov.app.worldishere.rest.GitHubApi
import com.kalabukhov.app.worldishere.rest.GitHubApiRepo
import com.kalabukhov.app.worldishere.server.PersonDB
import com.kalabukhov.app.worldishere.ui.EditPersonPresenter
import com.kalabukhov.app.worldishere.ui.main.FightAmazonkaPresenter
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.coroutineContext

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
    single { EventBus() } //  этот(EventBus) метод работает, а то что ниже нет
        // думаю что они возвращаю нулевое значение, нужно как то добавить контекст
    single { FightAmazonkaPresenter(Cicerone.create().router) }
    single { EditPersonPresenter(PersonDB(), Cicerone.create().router) }
    single { Cicerone.create().getNavigatorHolder() }
}
