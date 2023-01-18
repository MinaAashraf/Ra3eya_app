package com.mina.dev.ra3eya_app.domain.usecases

import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import javax.inject.Inject

class ReadAllChurchesUseCase @Inject constructor(private val churchRepository: ChurchRepository) {

    suspend fun execute () = churchRepository.readAllChurches()

}