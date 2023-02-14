package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.data.local.database.memberdb.MemberDao
import com.mina.dev.ra3eya_app.domain.model.Member
import javax.inject.Inject

class MemberLocalDataSourceImpl @Inject constructor(private var memberDao: MemberDao) :
    MemberLocalDataSource {

    override fun readMembersOfFamily(churchId: String, familyId: String): List<LiveData<Member>> =
        memberDao.readMembersOfFamily(churchId, familyId)

    override fun insertMember(member: Member) {
        memberDao.insertMember(member)
    }

    override fun searchMember(
        memberNameSubString: String,
        churchId: String
    ): List<LiveData<Member>> = memberDao.searchMember(memberNameSubString, churchId)


    override fun readMember(memberName: String, homeId: String): LiveData<Member> {
        return memberDao.readMember(memberName, homeId)
    }

    override fun readMembers(): LiveData<List<Member>> = memberDao.readMembers()

    override fun clearMembers() {
        memberDao.clearMembers()
    }

    override fun insertMembers(members: List<Member>) {
        memberDao.insertMembers(members)
    }


}