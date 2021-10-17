package com.kalabukhov.app.worldishere.impl

import android.app.Activity
import android.content.Intent
import com.kalabukhov.app.worldishere.domain.PersonModel
import com.kalabukhov.app.worldishere.ui.Router
import com.kalabukhov.app.worldishere.ui.main.MainActivity

class RouterImpl : Router {
    // для самописного cicerone
    override fun openMainScreen(activity: Activity, personModel: PersonModel?) {
        activity.startActivity(MainActivity.createLauncherIntent(activity, personModel))
    }
}