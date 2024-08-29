package com.umc6th.kioki.group

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemberEntity::class], version = 1)
abstract class GroupDatabase: RoomDatabase() {

    // DB 다루기 위한 DAO 객체를 반환하는 함수
    abstract fun memberDao(): MemberDao


    // Singleton 패턴
    companion object {
        private var instance: GroupDatabase? = null

        @Synchronized
        fun getInstance(context: Context): GroupDatabase? {
            if (instance == null) {
                synchronized(GroupDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GroupDatabase::class.java,   // 클래스 지정
                        "group-database"       // DB 이름 지정
                    ).allowMainThreadQueries().build()     // 메인 스레드에 쿼리작업 허용 (비효율적임)
                }
            }

            return instance
        }
    }
}