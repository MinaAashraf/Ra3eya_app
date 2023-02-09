package com.mina.dev.ra3eya_app.domain.model

data class Family(
     val familyName :String = "",
     val churchId :String = "",
     val homeId :String = "",
     val floorNum : Int = 0,
     val familyAddress : String = "",
     val persons : List<MemberNameId>? = null
)
