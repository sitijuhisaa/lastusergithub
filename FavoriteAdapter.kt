package com.dicoding.usergithub.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.usergithub.database.LikeUser
import com.dicoding.usergithub.databinding.ItemUserBinding

class FavoriteAdapter: ListAdapter<LikeUser, FavoriteAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: LikeUser) {
            binding.username.text = favoriteUser.username
            Glide.with(itemView.context)
                .load(favoriteUser.avatarUrl)
                .into(binding.image)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LikeUser>() {
            override fun areItemsTheSame(
                oldItem: LikeUser,
                newItem: LikeUser,
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: LikeUser,
                newItem: LikeUser,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}