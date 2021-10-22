package com.kalabukhov.app.worldishere.impl.util

import android.content.Context
import com.kalabukhov.app.worldishere.App

val Context.app: App
    get() {
        return applicationContext as App
    }