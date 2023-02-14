package com.mina.dev.ra3eya_app.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.datasource.MemberLocalDataSource
import com.mina.dev.ra3eya_app.data.remote.MemberRemoteDataSource
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.repository.MemberRepository
import com.mina.dev.ra3eya_app.domain.util.Result
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource,
    private val memberLocalDataSource: MemberLocalDataSource
) :
    MemberRepository {
    override suspend fun addMember(uri: Uri?, member: Member) =
        memberRemoteDataSource.addMember(uri, member)

    override suspend fun readMember(memberId: String, churchId: String): Result<Member> =
        memberRemoteDataSource.readMember(memberId, churchId)

    override fun readMembers(): LiveData<List<Member>> = memberLocalDataSource.readMembers()

    override suspend fun refreshMembers(churchId: String) {
        memberRemoteDataSource.readMembers(churchId).onSuccess {
            memberLocalDataSource.clearMembers()
            memberLocalDataSource.insertMembers(it)
        }
    }


}