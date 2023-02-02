package com.mina.dev.ra3eya_app.domain.repository

import android.net.Uri
import com.mina.dev.ra3eya_app.domain.model.Member

interface MemberRepository {
   suspend fun addMember (uri:Uri?, member:Member)
}