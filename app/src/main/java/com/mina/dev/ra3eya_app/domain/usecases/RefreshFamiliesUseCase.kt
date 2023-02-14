package com.mina.dev.ra3eya_app.domain.usecases

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.repository.FamilyRepository
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class RefreshFamiliesUseCase @Inject constructor(private val familyRepository: FamilyRepository) {
    suspend fun execute(churchId: String) =
        familyRepository.refreshAllFamilies(churchId)
}