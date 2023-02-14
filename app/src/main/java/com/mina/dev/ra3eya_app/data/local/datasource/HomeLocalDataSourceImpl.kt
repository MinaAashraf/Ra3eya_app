package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.database.homedb.HomeDao
import com.mina.dev.ra3eya_app.domain.model.Home
import javax.inject.Inject

class HomeLocalDataSourceImpl @Inject constructor(private val homeDao: HomeDao) :
    HomeLocalDataSource {
    override fun readHomes(): LiveData<List<Home>> = homeDao.readHomes()

    override fun insertHome(home: Home) {
        homeDao.insertHome(home)
    }

    override fun insertHomes(homes: List<Home>) {
        homeDao.clearHomes()
        homeDao.insertHomes(homes)
    }

    override fun searchHome(homeNameSubString: String, churchId: String): List<LiveData<Home>> =
        homeDao.searchHome(homeNameSubString, churchId)
}