package com.kalabukhov.app.worldishere

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.kalabukhov.app.worldishere.impl.RouterImpl
import com.kalabukhov.app.worldishere.ui.Router

// самодельный класс cicerone
//class App : Application() {
//    val router: Router = RouterImpl()
//}

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
}