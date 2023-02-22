package com.mina.dev.ra3eya_app.domain.usecases

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject


class ReadFamilyUseCase @Inject constructor(private val readFamilyRepo: FamilyRepository) {
    fun execute(familyName:String,homeId:String): LiveData<Family> {
        return readFamilyRepo.readFamily(familyName, homeId)
    }
}