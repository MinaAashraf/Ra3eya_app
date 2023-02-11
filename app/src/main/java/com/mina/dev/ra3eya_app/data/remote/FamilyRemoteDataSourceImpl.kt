package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class FamilyRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : FamilyRemoteDataSource {

    override suspend fun addFamily(
        context: Context,
        family: Family
    ): Result<FamilyNameId> {
        return withContext(Dispatchers.IO) {
            try {
                val churchRef = fireStore.collection(context.getString(R.string.church_key))
                val docSnapShot =
                    churchRef.document(family.churchId)
                        .collection(context.getString(R.string.families_collection_key)).add(family)
                        .await()
                val familyNameId = FamilyNameId(family.familyName, docSnapShot.id, family.floorNum)
                churchRef.document(family.churchId)
                    .collection(context.getString(R.string.homes_collection_key))
                    .document(family.homeId)
                    .update(
                        context.getString(R.string.families_field_key),
                        FieldValue.arrayUnion(familyNameId)
                    ).await()
                Result.Success(familyNameId)
            } catch (e: FirebaseException) {
                Result.Failure(e)
            }

        }
    }

    override suspend fun readFamily(
        context: Context,
        familyId: String,
        churchId: String
    ): Result<Family> {
        return withContext(Dispatchers.IO) {
            try {
                val docSnapShot =
                    fireStore.collection(context.getString(R.string.church_key)).document(churchId)
                        .collection(context.getString(R.string.families_collection_key))
                        .document(familyId).get().await()
                docSnapShot.toObject(Family::class.java)?.let {
                    Result.Success(it)
                } ?: Result.Failure(Exception(context.getString(R.string.err_try_again_message)))

            } catch (e: FirebaseException) {
                Result.Failure(e)

            }
        }
    }


}