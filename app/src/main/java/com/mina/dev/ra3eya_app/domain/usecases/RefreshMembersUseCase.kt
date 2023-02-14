package com.mina.dev.ra3eya_app.domain.usecases


import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import javax.inject.Inject

class RefreshMembersUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun execute(churchId: String) =
        memberRepository.refreshMembers(churchId)
}