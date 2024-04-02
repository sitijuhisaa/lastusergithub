package com.dicoding.usergithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: LikeUser)

    @Delete
    fun delete(user: LikeUser)

    @Query("SELECT * from LikeUser")
    fun getAllLike(): LiveData<List<LikeUser>>

    @Query("SELECT * FROM LikeUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<LikeUser>
}