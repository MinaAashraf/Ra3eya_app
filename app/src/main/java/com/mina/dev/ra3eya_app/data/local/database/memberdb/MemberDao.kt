package com.mina.dev.ra3eya_app.data.local.database.memberdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mina.dev.ra3eya_app.domain.model.*
import com.mina.dev.ra3eya_app.domain.util.Result

@Dao
interface MemberDao {

    @Query("Select * from member_table where churchId =:churchId and familyId = :familyId")
    fun readMembersOfFamily(churchId: String, familyId: String): List<LiveData<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member)

    @Query("select * from member_table where homeId like :memberNameSubString and churchId = :churchId")
    fun searchMember(memberNameSubString: String, churchId: String): List<LiveData<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMembers(members: List<Member>)

    @Query("Select * from member_table where homeId=:homeId and name=:memberName")
    fun readMember(memberName: String, homeId: String) : LiveData<Member>

    @Query("Select * from member_table")
    fun readMembers () : LiveData<List<Member>>

    @Query("DELETE FROM member_table")
    fun clearMembers()

}