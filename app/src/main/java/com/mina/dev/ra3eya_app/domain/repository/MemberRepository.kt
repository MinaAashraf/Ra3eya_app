package com.mina.dev.ra3eya_app.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.model.MemberNameId
import com.mina.dev.ra3eya_app.domain.util.Result

interface MemberRepository {
    suspend fun addMember(uri: Uri?, member: Member): Result<Member>
    fun readMember(memberName: String, homeId: String): LiveData<Member>
    suspend fun refreshMembers(churchId: String)
    fun readMembers(): LiveData<List<Member>>
    fun readMembersOfFamily(familyName: String, homeId:String) : LiveData<List<Member>>

}