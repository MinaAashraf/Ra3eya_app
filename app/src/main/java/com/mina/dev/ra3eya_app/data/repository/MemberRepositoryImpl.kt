package com.mina.dev.ra3eya_app.data.repository

import android.net.Uri
import com.mina.dev.ra3eya_app.data.remote.MemberRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(private val memberRemoteDataSource: MemberRemoteDataSource) :
    MemberRepository {
    override suspend fun addMember(uri: Uri?, member: Member) {
        memberRemoteDataSource.addMember(uri, member)
    }
}