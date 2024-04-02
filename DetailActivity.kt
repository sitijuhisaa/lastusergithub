package com.dicoding.usergithub.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.usergithub.R
import com.dicoding.usergithub.data.model.DetailUser
import com.dicoding.usergithub.database.LikeUser
import com.dicoding.usergithub.databinding.ActivityDetailBinding
import com.dicoding.usergithub.detail.follow.FollowFragment
import com.dicoding.usergithub.favorite.ViewModelFactory
import com.dicoding.usergithub.util.Result
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var isFavorite: Boolean = false

    private lateinit var username: String
    private lateinit var avatarUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username = intent.getStringExtra("username") ?: ""
        avatarUrl = intent.getStringExtra("avatarlurl") ?: ""

        detailViewModel.resultDetailUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as DetailUser
                    Glide.with(this)
                        .load(user.avatarUrl)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.ivImage)
                    binding.nama.text = user.name
                    binding.namapengguna.text = user.login
                    binding.followerscount.text = "${user.followers}"
                    binding.followingcount.text = "${user.following}"
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.ProgressBardetail.isVisible = it.isLoading
                }
            }
        }
        detailViewModel.getDetailUser(username)

        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )
        val titleFragment = mutableListOf(
            getString(R.string.followers), getString(R.string.following)
        )
        val adapter = DetailAdapter(this, fragments)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, posisi ->
            tab.text = titleFragment[posisi]
        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    detailViewModel.getFollowers(username)
                } else {
                    detailViewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        detailViewModel.getFollowers(username)

        detailViewModel.getFavoriteUserByUsername(username).observe(this) { favoriteUsers ->
            isFavorite = favoriteUsers != null
            if (isFavorite) {
                binding.btnfav.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.btnfav.context,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                binding.btnfav.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.btnfav.context,
                        R.drawable.ic_like
                    )
                )
            }
            binding.btnfav.setOnClickListener {
                val favoriteUser = LikeUser(username, avatarUrl)
                if (isFavorite) {
                    detailViewModel.delete(favoriteUser)
                    Toast.makeText(
                        this@DetailActivity,
                        "${username} deleted from favorites",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    detailViewModel.insert(favoriteUser)
                    Toast.makeText(
                        this@DetailActivity,
                        "${username} added to favorites",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }


}