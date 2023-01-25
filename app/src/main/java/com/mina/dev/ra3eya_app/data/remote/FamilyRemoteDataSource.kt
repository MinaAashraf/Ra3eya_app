package com.mina.dev.ra3eya_app.data.remote

import com.mina.dev.ra3eya_app.domain.util.Result

interface FamilyRemoteDataSource {
    fun addFamily(churchKey: String, homeIndex: Int) : Result<String>


}