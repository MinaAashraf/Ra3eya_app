package com.mina.dev.ra3eya_app.domain.usecases

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import javax.inject.Inject

class ReadAllChurchesUseCase @Inject constructor(private val churchRepository: ChurchRepository) {

    fun execute (): LiveData<List<Church>> = churchRepository.readAllChurches()

}