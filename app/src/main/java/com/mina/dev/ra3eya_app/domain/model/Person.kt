package com.mina.dev.ra3eya_app.domain.model

data class Person(
    val name: String,
    val idCardImage : String,
    val churchId: String,
    val homeId: String,
    val location: MyLocation
)
