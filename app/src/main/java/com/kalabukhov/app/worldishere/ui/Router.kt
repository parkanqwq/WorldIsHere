package com.kalabukhov.app.worldishere.ui

import android.app.Activity
import com.kalabukhov.app.worldishere.domain.PersonModel

interface Router {
    fun openMainScreen(activity: Activity, personModel: PersonModel?)
}