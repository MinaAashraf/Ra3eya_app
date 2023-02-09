package com.mina.dev.ra3eya_app.domain.usecases

import android.net.Uri
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import javax.inject.Inject

class AddMemberUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun execute(uri: Uri?, member: Member) = memberRepository.addMember(uri, member)

}