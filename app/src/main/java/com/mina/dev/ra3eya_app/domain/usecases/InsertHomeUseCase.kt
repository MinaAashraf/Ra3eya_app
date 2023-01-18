package com.mina.dev.ra3eya_app.domain.usecases

import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class InsertHomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend fun execute(home: Home): Result<String> {
       return homeRepository.insertHome(home)
    }
}