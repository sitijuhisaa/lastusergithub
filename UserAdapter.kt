package com.dicoding.usergithub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.usergithub.data.model.ItemsItem
import com.dicoding.usergithub.databinding.ItemUserBinding

class UserAdapter(private val data: MutableList<ItemsItem> = mutableListOf(), private val listener:(ItemsItem) -> Unit)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<ItemsItem>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val bind : ItemUserBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind(item: ItemsItem) {
            Glide.with(bind.root.context)
                .load(item.avatarUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
                .into(bind.image)
            bind.username.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent, false))


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}