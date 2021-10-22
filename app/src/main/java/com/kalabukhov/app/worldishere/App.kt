package com.kalabukhov.app.worldishere

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import com.kalabukhov.app.worldishere.bus.EventBus

// самодельный класс cicerone
//class App : Application() {
//    val router: Router = RouterImpl()
//}

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    val imAttackBus = EventBus()
    val amazonkaAttackBus = EventBus()
}

val Fragment.app: App
    get() = requireContext().applicationContext as App

val Context.app: App
    get() = applicationContext as App