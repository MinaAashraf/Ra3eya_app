package com.mina.dev.ra3eya_app.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.data.local.database.churchdb.ChurchDao
import com.mina.dev.ra3eya_app.data.local.database.churchdb.ChurchDb
import com.mina.dev.ra3eya_app.data.local.database.familydb.FamilyDao
import com.mina.dev.ra3eya_app.data.local.database.familydb.FamilyDb
import com.mina.dev.ra3eya_app.data.local.database.homedb.HomeDao
import com.mina.dev.ra3eya_app.data.local.database.homedb.HomeDb
import com.mina.dev.ra3eya_app.data.local.database.memberdb.MemberDao
import com.mina.dev.ra3eya_app.data.local.database.memberdb.MemberDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    // firebase
    @Singleton
    @Provides
    fun getFireStoreInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun getFirebaseStorageInstance(): StorageReference = FirebaseStorage.getInstance().reference


    // shared pref
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.shared_preference_file_key), 0)


    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor =
        sharedPreferences.edit()

    // room

    @Singleton
    @Provides
    fun provideChurchDatabase(@ApplicationContext context: Context): ChurchDb =
        Room.databaseBuilder(
            context,
            ChurchDb::class.java,
            "church_table"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideChurchDao(churchDb: ChurchDb): ChurchDao = churchDb.getDao()


    @Singleton
    @Provides
    fun provideHomeDatabase(@ApplicationContext context: Context): HomeDb =
        Room.databaseBuilder(
            context,
            HomeDb::class.java,
            "home_table"
        ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideHomeDao(homeDb: HomeDb): HomeDao = homeDb.getDao()


    @Singleton
    @Provides
    fun provideFamilyDatabase(@ApplicationContext context: Context): FamilyDb =
        Room.databaseBuilder(
            context,
            FamilyDb::class.java,
            "family_table"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideFamilyDao(familyDb: FamilyDb): FamilyDao = familyDb.getDao()


    @Singleton
    @Provides
    fun provideMemberDatabase(@ApplicationContext context: Context): MemberDb =
        Room.databaseBuilder(
            context,
            MemberDb::class.java,
            "member_table"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMemberDao(memberDao: MemberDb): MemberDao = memberDao.getDao()


}