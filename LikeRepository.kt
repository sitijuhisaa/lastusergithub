package com.dicoding.usergithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.usergithub.database.LikeDao
import com.dicoding.usergithub.database.LikeDatabase
import com.dicoding.usergithub.database.LikeUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LikeRepository(application: Application) {
    private val likeDao : LikeDao
    private val appExecutors : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = LikeDatabase.getDatabase(application)
        likeDao = db.likeDao()
    }

    fun insert(user: LikeUser){
        appExecutors.execute { likeDao.insert(user) }
    }

    fun delete(user: LikeUser){
        appExecutors.execute { likeDao.delete(user) }
    }

    fun getAllLike() : LiveData<List<LikeUser>> = likeDao.getAllLike()

    fun getFavoriteUserByUsername(username: String): LiveData<LikeUser> = likeDao.getFavoriteUserByUsername(username)

}

