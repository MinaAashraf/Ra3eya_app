package com.mina.dev.ra3eya_app.domain.usecases

import com.mina.dev.ra3eya_app.data.remote.HomeRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class ReadHomesUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend fun execute(churchId: String): Result<List<Home>> =
        homeRepository.readHomes(churchId)
}