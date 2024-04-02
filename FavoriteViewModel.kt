package com.dicoding.usergithub.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usergithub.database.LikeUser
import com.dicoding.usergithub.repository.LikeRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mLikeRepository: LikeRepository = LikeRepository(application)

    fun insert(user: LikeUser){
        mLikeRepository.insert(user)
    }

    fun delete(user: LikeUser){
        mLikeRepository.delete(user)
    }

    fun getAllLike() : LiveData<List<LikeUser>> = mLikeRepository.getAllLike()
}