package com.mina.dev.ra3eya_app.data.local.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.database.churchdb.ChurchDao
import com.mina.dev.ra3eya_app.domain.model.Church
import javax.inject.Inject

class ChurchLocalDataSourceImpl @Inject constructor(private val churchDao : ChurchDao) : ChurchLocalDataSource {
    override fun readChurch(churchName: String,churchPassword: String): LiveData<Church> =  churchDao.readChurch(churchName,churchPassword)


    override fun readAllChurches(): LiveData<List<Church>> = churchDao.readAllChurches()

    override suspend fun addChurch(church: Church) {
      churchDao.addChurch(church)
    }

    override suspend fun addAllChurches(churches: List<Church>) {
        churchDao.addAllChurches(churches)
      //  Log.d("count:", "${churchDao.getSize()}   , ${churchDao.readChurch("كنيسة القديس ابانوب","١٢٣٤٥٦٧٨").value}")
    }

    override fun signIn(churchName: String, churchPassword: String): LiveData<Church> {
        return readChurch(churchName, churchPassword)
    }

    override suspend fun clearChurches() {
        churchDao.clearChurches()
    }
}