package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.util.Result

interface FamilyRemoteDataSource {
    suspend fun addFamily(context: Context, family: Family) : Result<FamilyNameId>
    suspend fun readFamily (context: Context, familyId :String , churchId : String) : Result<Family>
}