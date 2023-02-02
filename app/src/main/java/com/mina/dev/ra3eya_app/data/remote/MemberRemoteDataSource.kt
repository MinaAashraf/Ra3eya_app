package com.mina.dev.ra3eya_app.data.remote

import android.net.Uri
import com.mina.dev.ra3eya_app.domain.model.Member

interface MemberRemoteDataSource {

   suspend fun addMember(uri: Uri?,member:Member)

}