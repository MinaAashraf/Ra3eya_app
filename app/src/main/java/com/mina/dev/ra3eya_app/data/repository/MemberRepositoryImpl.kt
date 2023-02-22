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
        memberRemoteDataSource.addMember(uri, member).onSuccess {
            memberLocalDataSource.insertMember(it)
        }


    override fun readMember(memberName: String, homeId: String): LiveData<Member> =
        memberLocalDataSource.readMember(memberName, homeId)

    override fun readMembers(): LiveData<List<Member>> = memberLocalDataSource.readMembers()

    override fun readMembersOfFamily(familyName: String, homeId:String): LiveData<List<Member>> {
       return  memberLocalDataSource.readMemberOfFamily(familyName, homeId)
    }

    override suspend fun refreshMembers(churchId: String) {
        memberRemoteDataSource.readMembers(churchId).onSuccess {
            memberLocalDataSource.clearMembers()
            memberLocalDataSource.insertMembers(it)
        }
    }


}