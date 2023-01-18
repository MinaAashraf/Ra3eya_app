package com.mina.dev.ra3eya_app.domain.model

data class Family(
    private val churchId :String,
    private val homeId :String,
    private val persons : List<String>
)
