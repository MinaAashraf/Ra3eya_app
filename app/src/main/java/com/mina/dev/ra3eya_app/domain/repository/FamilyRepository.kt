package com.mina.dev.ra3eya_app.domain.repository

import android.content.Context
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.util.Result

interface FamilyRepository {
    suspend fun addFamily( family: Family): Result<FamilyNameId>
    suspend fun readFamily(
        familyId: String,
        churchId: String
    ) : Result<Family>
}