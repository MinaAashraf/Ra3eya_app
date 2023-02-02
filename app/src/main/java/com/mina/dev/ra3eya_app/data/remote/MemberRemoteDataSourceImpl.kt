package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Member
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.net.URI
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: StorageReference,
    @ApplicationContext private val context: Context
) : MemberRemoteDataSource {
    override suspend fun addMember(uri: Uri?, member: Member) {
        try {
            uri?.let {
                val taskSnapShot =
                    storage.child(context.getString(R.string.storage_folder_key)).putFile(uri)
                        .await()
                val uri = taskSnapShot.metadata!!.reference!!.downloadUrl.await()
                val url = uri.toString()
                member.idImage = url
                Log.d("id image url", url)
            }
            val docRef =
                fireStore.collection(context.getString(R.string.member_key)).add(member).await()

        } catch (e: Exception) {

        }

    }
}