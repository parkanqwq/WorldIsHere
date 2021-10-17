package com.kalabukhov.app.worldishere.domain

import io.reactivex.Observable

interface PersonRepo {
    fun change(personName: String)
    val person: Observable<String>
}