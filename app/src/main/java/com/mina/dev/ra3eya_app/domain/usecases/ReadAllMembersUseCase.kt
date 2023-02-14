package com.mina.dev.ra3eya_app.domain.usecases

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.repository.HomeRepository
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import javax.inject.Inject


class ReadAllMembersUseCase @Inject constructor(private val membersRepository: MemberRepository) {
    fun execute(): LiveData<List<Member>> =
        membersRepository.readMembers()
}