package com.mina.dev.ra3eya_app.domain.usecases

import com.mina.dev.ra3eya_app.data.remote.HomeRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import javax.inject.Inject

class ReadMemberUseCase @Inject constructor(private val memberRepository: MemberRepository) {
     fun execute(memberName : String,homeId:String) =
        memberRepository.readMember(memberName, homeId)
}