package com.mina.dev.ra3eya_app.domain.usecases

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val churchRepository: ChurchRepository) {
     fun execute(church: ChurchCredentials): LiveData<Church> = churchRepository.signIn(church)
}