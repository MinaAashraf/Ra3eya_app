package com.mina.dev.ra3eya_app.domain.usecases

import android.content.Context
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import javax.inject.Inject

class AddFamilyUseCase @Inject constructor(private val familyRepository: FamilyRepository) {
     suspend fun addFamily (family:Family) = familyRepository.addFamily(family)
}