package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.model.MemberNameId
import com.mina.dev.ra3eya_app.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.URI
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: StorageReference,
    @ApplicationContext private val context: Context
) : MemberRemoteDataSource {
    override suspend fun addMember(uri: Uri?, member: Member): Result<MemberNameId> {
        return withContext(Dispatchers.IO)
        {
            try {
                uri?.let {
                    val taskSnapShot =
                        storage.child(context.getString(R.string.storage_folder_key)).putFile(uri)
                            .await()
                    val uri = taskSnapShot.metadata!!.reference!!.downloadUrl.await()
                    val url = uri.toString()
                    member.idImage = url
                }

                val churchRef = fireStore.collection(context.getString(R.string.church_key))
                val docSnapShot = churchRef.document(member.churchId)
                    .collection(context.getString(R.string.member_key)).add(member).await()

                val memberNameId = MemberNameId(member.name, docSnapShot.id)

                churchRef.document(member.churchId)
                    .collection(context.getString(R.string.families_collection_key))
                    .document(member.familyId)
                    .update(
                        context.getString(R.string.persons_filed_key),
                        FieldValue.arrayUnion(memberNameId)
                    ).await()

                Result.Success(memberNameId)
            } catch (e: FirebaseException) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun readMember(memberId: String, churchId: String): Result<Member> {
        return withContext(Dispatchers.IO)
        {
            try {
                val docSnapShot =
                    fireStore.collection(context.getString(R.string.church_key)).document(churchId)
                        .collection(context.getString(R.string.member_key)).document(memberId).get()
                        .await()

                val member = docSnapShot.toObject(Member::class.java)
                member?.let { Result.Success(it) }
                    ?: Result.Failure(Exception(context.getString(R.string.err_try_again_msg)))

            } catch (e: FirebaseException) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun readMembers(churchId: String): Result<List<Member>> {
        return withContext(Dispatchers.IO) {
            try {
                val docSnapShot =
                    fireStore.collection(context.getString(R.string.church_key)).document(churchId)
                        .collection(context.getString(R.string.member_key))
                        .get().await()
                val members = mutableListOf<Member>()
                docSnapShot.documents.forEach {
                    it.toObject(Member::class.java)?.let { member ->
                        members.add(member)
                    }
                }
                Result.Success(members)

            } catch (e: FirebaseException) {
                Result.Failure(e)

            }
        }
    }

}