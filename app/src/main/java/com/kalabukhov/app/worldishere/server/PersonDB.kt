package com.kalabukhov.app.worldishere.server

import com.kalabukhov.app.worldishere.domain.PersonModel

class PersonDB {
    fun getAllPerson() : ArrayList<PersonModel> {
        val personData: ArrayList<PersonModel> = arrayListOf()
        personData.add(PersonModel("1", "Max", 12, 32))
        personData.add(PersonModel("2", "Jak", 22, 43))
        personData.add(PersonModel("3", "Bob", 14, 39))
        return personData
    }
}



