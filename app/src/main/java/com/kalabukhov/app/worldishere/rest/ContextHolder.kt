package com.kalabukhov.app.worldishere.rest

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object ContextHolder {
    private lateinit var context: Context

    fun getContext(): Context {
        return context
    }

    fun initHolder(context: Context) {
        this.context = context
    }
}