package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.data.local.datasource.ChurchLocalDataSource
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

class ChurchRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : ChurchRemoteDataSource {


    override suspend fun readChurch(churchId: String): Result<Church> {
        return withContext(Dispatchers.IO) {
            try {
                val snapShot =
                    fireStore.collection(context.getString(R.string.church_key)).document(churchId)
                        .get().await()
                snapShot?.let {
                    it.toObject(Church::class.java)?.let { church ->
                        Result.Success(church)
                    }
                } ?: kotlin.run {
                    Result.Failure(Throwable("هناك مشكلة..حاول مرة اخري"))
                }
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun readAllChurches(): Result<List<Church>> {
        return withContext(Dispatchers.IO) {
            try {
                val churches = mutableListOf<Church>()
                val snapShot =
                    fireStore.collection("Church").get().await()
                snapShot.documents.forEach {
                    it?.let { doc ->
                        val church = doc.toObject(Church::class.java)!!
                        churches.add(church)
                    }
                }
                Result.Success(churches)

            } catch (e: Exception) {
                Result.Failure(e)
            }
        }

    }

    override suspend fun addChurch(church: Church): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                fireStore.collection(context.getString(R.string.church_key)).document(church.id)
                    .set(church).await()
                Result.Success(context.getString(R.string.addingChurch_successfully_msg))
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }


    override suspend fun signIn(church: ChurchCredentials): Result<Church> {
        return withContext(Dispatchers.IO)
        {
            try {
                val docSnapShot = fireStore.collection(context.getString(R.string.church_key))
                    .document(church.name).get()
                    .await()
                return@withContext if (docSnapShot != null && docSnapShot.exists() && docSnapShot.data != null)
                    Result.Success(docSnapShot.toObject(Church::class.java)!!)
                else
                    return@withContext Result.Failure(Throwable(context.getString(R.string.wrong_password_msg)))
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }


}
