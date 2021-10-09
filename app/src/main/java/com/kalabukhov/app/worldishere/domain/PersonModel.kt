package com.kalabukhov.app.worldishere.domain

import java.io.Serializable

data class PersonModel(
    val id: Int,
    val name: String,
    val age: Int,
    val power: Int
) : Serializable
