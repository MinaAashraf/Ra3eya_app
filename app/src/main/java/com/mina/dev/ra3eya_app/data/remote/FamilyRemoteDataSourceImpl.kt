package com.mina.dev.ra3eya_app.data.remote

import android.content.Context
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.util.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FamilyRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : FamilyRemoteDataSource {

    override fun addFamily(churchId: String, homeIndex: Int): Result<String> {
        return  Result.Success("")
    }

}