package com.kalabukhov.app.worldishere

import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.kalabukhov.app.worldishere.domain.PersonModel
import com.kalabukhov.app.worldishere.ui.less5.GitHubTab
import com.kalabukhov.app.worldishere.ui.main.MainActivity

object Screens {
    fun Main(personModel: PersonModel?) = ActivityScreen {
            context -> MainActivity.createLauncherIntent(context, personModel)
    }
    fun GitHub() = ActivityScreen {
            context -> GitHubTab.createLauncherIntentLesson5(context)
    }
}