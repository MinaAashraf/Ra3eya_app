package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : HomeRemoteDataSource {

    override suspend fun insertHome(home: Home): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
              val snapShot = fireStore.collection(context.getString(R.string.church_key))
                    .document(home.churchId!!)
                    .collection(context.getString(R.string.homes_collection_key)).add(home).await()
                snapShot.update(context.getString(R.string.homeId_key),snapShot.id)
                Result.Success(context.getString(R.string.addingHome_successfully_msg))
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun readHomes(churchId: String): Result<List<Home>> {
        return withContext(Dispatchers.IO) {
            try {
                val querySnapShot =
                    fireStore.collection(context.getString(R.string.church_key)).document(churchId)
                        .collection(context.getString(R.string.homes_collection_key)).get().await()
                val homes = mutableListOf<Home>()
                querySnapShot.documents.forEach {
                    it?.let {
                        homes.add(it.toObject(Home::class.java)!!)
                    }
                }
                Result.Success(homes)
            } catch (e: FirebaseException) {
                Result.Failure(e)
            }
        }
    }

}