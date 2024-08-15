package com.umc6th.kioki

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemberDao {

    @Insert
    fun insert(member: MemberEntity)

    @Update
    fun update(member: MemberEntity)

    @Delete
    fun delete(member: MemberEntity)

    @Query("SELECT * FROM MemberTable")
    fun getMembers(): List<MemberEntity>

    @Query("SELECT * FROM MemberTable WHERE id = :id")
    fun getMember(id: Int): MemberEntity
}