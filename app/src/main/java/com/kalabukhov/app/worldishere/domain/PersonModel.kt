package com.kalabukhov.app.worldishere.domain

import java.io.Serializable

data class PersonModel(
    val id: String,
    val name: String,
    val age: Int,
    val power: Int
) : Serializable
