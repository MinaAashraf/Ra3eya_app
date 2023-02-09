package com.mina.dev.ra3eya_app.domain.repository

import android.net.Uri
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.model.MemberNameId
import com.mina.dev.ra3eya_app.domain.util.Result

interface MemberRepository {
   suspend fun addMember (uri:Uri?, member:Member) : Result<MemberNameId>
   suspend fun readMember (memberId : String, churchId : String) : Result<Member>

}