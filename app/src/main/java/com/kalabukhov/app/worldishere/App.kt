package com.kalabukhov.app.worldishere

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import com.kalabukhov.app.worldishere.di.dbModuleUsers
import com.kalabukhov.app.worldishere.di.mvpModule
import com.kalabukhov.app.worldishere.di.retrofitModule
import com.kalabukhov.app.worldishere.rest.ContextHolder
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// самодельный класс cicerone
//class App : Application() {
//    val router: Router = RouterImpl()
//}

class App : Application() {
//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            UserDb::class.java,
//            "users.db"
//        ).build()
//    }
//    private val userDao by lazy { db.userDoa() }
//    val userRepo by lazy { UserRoomRepoImpl(userDao) }

//    private val dbRepositories by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            UserDbRepositories::class.java,
//            "repositories.db"
//        ).build()
//    }
//    private val userRepositoriesDao by lazy { dbRepositories.userRepositoriesDao() }
//    val userRepositoriesRepo by lazy { UserRoomRepoRepositoriesImpl(userRepositoriesDao) }

//    private val retrofit =  Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(MoshiConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//        .build()
//    val gitHabUsersApi: GitHubApi = retrofit.create(GitHubApi::class.java)
//    val gitHabUsersApiRepo: GitHubApiRepo = retrofit.create(GitHubApiRepo::class.java)

    // попытался ойти от Cicerone через модули, коин, но что то пошло не так и не работает
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

//    val imAttackBus = EventBus()
//    val amazonkaAttackBus = EventBus()

    override fun onCreate() {
        super.onCreate()
        initDependencies()
        startKoin {
            androidContext(this@App)
            modules(dbModuleUsers, retrofitModule, mvpModule)
        }
    }

    private fun initDependencies() {
        ContextHolder.initHolder(this)
    }
}

val Fragment.app: App
    get() = requireContext().applicationContext as App

val Context.app: App
    get() = applicationContext as App