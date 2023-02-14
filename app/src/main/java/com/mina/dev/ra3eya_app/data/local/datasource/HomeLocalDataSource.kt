package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Home

interface HomeLocalDataSource {

    fun readHomes(): LiveData<List<Home>>
    fun insertHome(home: Home)
    fun insertHomes(homes: List<Home>)
    fun searchHome(homeNameSubString: String, churchId: String): List<LiveData<Home>>


}