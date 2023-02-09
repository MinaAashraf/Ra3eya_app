package com.mina.dev.ra3eya_app.domain.usecases

import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject


class ReadFamilyUseCase @Inject constructor(private val readFamilyRepo: FamilyRepository) {
    suspend fun execute(familyId:String,churchId:String): Result<Family> {
        return readFamilyRepo.readFamily(familyId, churchId)
    }
}