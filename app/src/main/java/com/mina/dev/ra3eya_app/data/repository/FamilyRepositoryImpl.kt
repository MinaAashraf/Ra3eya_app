package com.mina.dev.ra3eya_app.data.repository

import android.content.Context
import com.mina.dev.ra3eya_app.data.remote.FamilyRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyRemoteDataSource: FamilyRemoteDataSource,
    @ApplicationContext private val context: Context

) :
    FamilyRepository {
    override suspend fun addFamily( family: Family): Result<FamilyNameId> =
        familyRemoteDataSource.addFamily(context, family)

    override suspend fun readFamily( familyId: String, churchId: String) =
        familyRemoteDataSource.readFamily(context, familyId, churchId)


}