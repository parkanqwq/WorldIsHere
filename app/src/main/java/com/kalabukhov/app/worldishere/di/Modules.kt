package com.kalabukhov.app.worldishere.di

import android.content.Context
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.kalabukhov.app.worldishere.bus.EventBus
import com.kalabukhov.app.worldishere.data.*
import com.kalabukhov.app.worldishere.domain.UsersRepo
import com.kalabukhov.app.worldishere.domain.UsersRepositoriesRepo
import com.kalabukhov.app.worldishere.rest.GitHubApi
import com.kalabukhov.app.worldishere.rest.GitHubApiRepo
import com.kalabukhov.app.worldishere.ui.less5.GitHubTab
import com.kalabukhov.app.worldishere.ui.less5.GitHubUsersRepoActivity
import com.kalabukhov.app.worldishere.ui.main.FightAmazonkaPresenter
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

// закоментил коин, он работает

//val dbModuleUsers = module {
//    val dbPatch = "users.db"
//    single { Room.databaseBuilder(get(), UserDb::class.java, dbPatch).build() }
//    single { get<UserDb>().userDoa() }
//    single<UsersRepo> { UserRoomRepoImpl(get()) }
//
//    val dbPatchRepositories = "repositories.db"
//    single { Room.databaseBuilder(get(), UserDbRepositories::class.java, dbPatchRepositories).build() }
//    single { get<UserDbRepositories>().userRepositoriesDao() }
//    single<UsersRepositoriesRepo> { UserRoomRepoRepositoriesImpl(get()) }
//}
//
//val retrofitModule = module {
//    factory<GitHubApi> { Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(MoshiConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//        .build().create(GitHubApi::class.java) }
//
//    factory<GitHubApiRepo> { Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(MoshiConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//        .build().create(GitHubApiRepo::class.java) }
//}

val mvpModule = module {
    single { EventBus() }
    // ниже то что не работает, конечно пробовал и болеее разные методы, ни чего не видит он
//    single { Cicerone.create() }
//    single { get<Cicerone.Companion>().create().router }
//    single { PersonDB() }
//    factory { EditPersonPresenter(get(), get()) }
//    factory { FightAmazonkaPresenter(get()) }

}
//
//class gaga {
//    val c = Cicerone.create().getNavigatorHolder()
////    fun g() = c.getNavigatorHolder()
//
//}


@Module
class DbModuleUsers(private val context: Context) {
//    @Provides
//    @Singleton
//    fun provideContext(): Context = context

//    @Provides
//    @Singleton
//    fun provideDbPatch(): String = "users.db"

    @Provides
    fun provideUsersRepo(userDoa: UserDao): UsersRepo = UserRoomRepoImpl(userDoa)

    @Provides
    @Singleton
    fun provideUserDoa(userDb: UserDb): UserDao = userDb.userDoa()

    @Provides
    @Singleton
    fun provideUsersDb(): UserDb =
        Room.databaseBuilder(context, UserDb::class.java, "users.db").build()

}

@Module
class DbModuleUsersRepositories(private val contextRepos: Context) {
    // кстати, делал как на уроке, не собирается проект. ниже 2 метода не работают, не пойму почему
    // помогите с этим разобраться
//    @Provides
//    @Singleton
//    fun provideContextRepos(): Context = contextRepos

//    @Provides
//    @Singleton
//    fun provideDbPatchRepos(): String = "repositories.db"

    @Provides
    fun provideUsersRepo(userDoa: UserRepositoriesDao): UsersRepositoriesRepo
    = UserRoomRepoRepositoriesImpl(userDoa)

    @Provides
    @Singleton
    fun provideUserDoa(userDb: UserDbRepositories): UserRepositoriesDao
    = userDb.userRepositoriesDao()

    @Provides
    @Singleton
    fun provideUsersDb(): UserDbRepositories =
        Room.databaseBuilder(contextRepos, UserDbRepositories::class.java, "repositories.db").build()

}

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubApi =
        Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build().create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun provideGitHubApiRepo(): GitHubApiRepo =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build().create(GitHubApiRepo::class.java)
}

//@Module
//class EventBus {
//    @Provides
//    @Singleton
//    fun provideEventBus(): EventBus = EventBus()
//}

// пробовал так, тоже фигня какаята, даже не пойму как его вставить
@Module
class CiceroneFun {
    @Provides
    @Singleton
    fun provideCicerone(c: Cicerone.Companion): Cicerone.Companion
    = Cicerone
}

@Singleton
@Component(modules = [DbModuleUsers::class, RetrofitModule::class, DbModuleUsersRepositories::class, CiceroneFun::class])
interface AppComponent {
    fun inject(activity: GitHubTab)
    fun inject(activity: GitHubUsersRepoActivity)
    //fun getEventBuss()
    fun inject(activity: Cicerone.Companion)
}

//private val cicerone = Cicerone.create()
//val router get() = cicerone.router
//val navigatorHolder get() = cicerone.getNavigatorHolder()
