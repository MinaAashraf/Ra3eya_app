package com.mina.dev.ra3eya_app.domain.usecases

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import javax.inject.Inject


class ReadFamiliesOfHomeUseCase @Inject constructor(private val familyRepository: FamilyRepository) {
    fun execute(homeId:String): LiveData<List<Family>> =
        familyRepository.readFamiliesOfHome(homeId)
}