package com.kalabukhov.app.worldishere

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.kalabukhov.app.worldishere.bus.EventBus
import com.kalabukhov.app.worldishere.data.UserDb
import com.kalabukhov.app.worldishere.data.UserRoomRepoImpl
import com.kalabukhov.app.worldishere.rest.ContextHolder
import com.kalabukhov.app.worldishere.rest.GitHubApi
import com.kalabukhov.app.worldishere.rest.GitHubApiRepo
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

// самодельный класс cicerone
//class App : Application() {
//    val router: Router = RouterImpl()
//}

class App : Application() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserDb::class.java,
            "users.db"
        ).build()
    }
    private val userDao by lazy { db.userDoa() }
    val userRepo by lazy { UserRoomRepoImpl(userDao) }

    private val retrofit =  Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
    val gitHabUsersApi: GitHubApi = retrofit.create(GitHubApi::class.java)
    val gitHabUsersApiRepo: GitHubApiRepo = retrofit.create(GitHubApiRepo::class.java)

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    val imAttackBus = EventBus()
    val amazonkaAttackBus = EventBus()

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        ContextHolder.initHolder(this)
    }
}

val Fragment.app: App
    get() = requireContext().applicationContext as App

val Context.app: App
    get() = applicationContext as App