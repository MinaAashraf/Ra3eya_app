package com.mina.dev.ra3eya_app.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.datasource.FamilyLocalDataSource
import com.mina.dev.ra3eya_app.data.remote.FamilyRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyRemoteDataSource: FamilyRemoteDataSource,
    private val familyLocalDataSource: FamilyLocalDataSource,
    @ApplicationContext private val context: Context
) :
    FamilyRepository {
    override suspend fun addFamily(family: Family): Result<FamilyNameId> {
        return familyRemoteDataSource.addFamily(context, family).onSuccess {
            familyLocalDataSource.insertFamily(family)
        }
    }

    override fun readFamily(familyName: String, homeId: String): LiveData<Family> =
        familyLocalDataSource.readFamily(familyName,homeId)

    override fun readFamilies(): LiveData<List<Family>> = familyLocalDataSource.readFamilies()

    override suspend fun refreshAllFamilies(churchId: String) {
        familyRemoteDataSource.readAllFamilies(churchId).onSuccess {
            familyLocalDataSource.clearAllFamilies()
            familyLocalDataSource.insertAllFamilies(it)
        }
    }

    override suspend fun searchFamily(familyName: String): LiveData<List<Family>> {
        return familyLocalDataSource.searchFamily(familyName)
    }

    override fun readFamiliesOfHome(homeId: String): LiveData<List<Family>> {
        return familyLocalDataSource.readAllFamiliesOfHome(homeId)
    }


}