package com.mina.dev.ra3eya_app.data.local.datasource

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mina.dev.ra3eya_app.domain.model.Member

interface MemberLocalDataSource {
    fun readMembersOfFamily(churchId: String, familyId: String): List<LiveData<Member>>

    fun insertMember(member: Member)

    fun searchMember(memberNameSubString: String, churchId: String): List<LiveData<Member>>

    fun readMember(memberName: String,homeId:String) :LiveData<Member>

    fun readMembers() :LiveData<List<Member>>

    fun clearMembers()

    fun insertMembers(members: List<Member>)
}