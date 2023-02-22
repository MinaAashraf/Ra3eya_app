package com.mina.dev.ra3eya_app.data.local.database.memberdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mina.dev.ra3eya_app.domain.model.*
import com.mina.dev.ra3eya_app.domain.util.Result

@Dao
interface MemberDao {

    @Query("Select * from member_table where familyName = :familyName and homeId = :homeId")
    fun readMembersOfFamily(familyName: String, homeId:String): LiveData<List<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member)

    @Query("select * from member_table where homeId like :memberNameSubString and churchId = :churchId")
    fun searchMember(memberNameSubString: String, churchId: String): LiveData<List<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMembers(members: List<Member>)

    @Query("Select * from member_table where homeId=:homeId and name=:memberName")
    fun readMember(memberName: String, homeId: String) : LiveData<Member>

    @Query("Select * from member_table")
    fun readMembers () : LiveData<List<Member>>

    @Query("DELETE FROM member_table")
    fun clearMembers()

}