package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.database.churchdb.ChurchDao
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class ChurchLocalDataSourceImpl @Inject constructor(private val churchDao : ChurchDao) : ChurchLocalDataSource {
    override fun readChurch(churchName: String,churchPassword: String): LiveData<Church> =  churchDao.readChurch(churchName, churchPassword)

    override fun readAllChurches(): LiveData<List<Church>> = churchDao.readAllChurches()

    override suspend fun addChurch(church: Church) {
      churchDao.addChurch(church)
    }

    override suspend fun addAllChurches(churches: List<Church>) {
        churchDao.addAllChurches(churches)
    }

    override fun signIn(churchName: String, churchPassword: String): LiveData<Church> =
       churchDao.readChurch(churchName, churchPassword)
}