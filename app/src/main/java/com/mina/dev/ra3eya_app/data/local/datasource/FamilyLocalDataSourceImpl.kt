package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.database.familydb.FamilyDao
import com.mina.dev.ra3eya_app.domain.model.Family
import javax.inject.Inject

class FamilyLocalDataSourceImpl @Inject constructor(private val familyDao: FamilyDao) :
    FamilyLocalDataSource {
    override fun readAllFamiliesOfHome(churchId: String, homeId: String) =
        familyDao.readAllFamiliesOfHome(churchId, homeId)

    override suspend fun insertFamily(family: Family) {
        familyDao.insertFamily(family)
    }

    override suspend fun insertAllFamilies(families: List<Family>) {
        familyDao.insertAllFamilies(families)
    }

    override fun readFamily(familyName: String): LiveData<Family> =
        familyDao.readFamily(familyName)

    override fun readFamilies(): LiveData<List<Family>> = familyDao.readFamilies()

    override fun searchFamily(familyNameSubString: String): List<LiveData<Family>> =
        familyDao.searchFamily(familyNameSubString)

    override fun clearAllFamilies() {
        familyDao.clearFamilies()
    }
}