package com.kalabukhov.app.worldishere

import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.kalabukhov.app.worldishere.domain.PersonModel
import com.kalabukhov.app.worldishere.ui.main.MainActivity

object Screens {
    fun Main(personModel: PersonModel?) = ActivityScreen {
            context -> MainActivity.createLauncherIntent(context, personModel)
    }
}