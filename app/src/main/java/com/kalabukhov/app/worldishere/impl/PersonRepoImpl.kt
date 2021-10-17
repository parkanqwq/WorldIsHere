package com.kalabukhov.app.worldishere.impl

import com.kalabukhov.app.worldishere.domain.PersonRepo
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class PersonRepoImpl: PersonRepo {
    private val behaviorSubject = BehaviorSubject.create<String>()

    override fun change(personName: String) {
        behaviorSubject.onNext(personName)
    }

    override val person: Observable<String>
        get() = behaviorSubject
}