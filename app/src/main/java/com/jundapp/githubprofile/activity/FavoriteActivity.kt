package com.jundapp.githubprofile.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.adapter.UserListAdapter
import com.jundapp.githubprofile.databinding.ActivityFavoriteBinding
import com.jundapp.githubprofile.models.UserModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.favorite_title)

        val listUserAdapter = UserListAdapter(this, listOf(
            UserModel("specialOne16", 1, null)
        ))
        binding.listFavorite.layoutManager = LinearLayoutManager(this)

        listUserAdapter.setOnItemClickCallback(object :
            UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val toDetail = Intent(
                    this@FavoriteActivity,
                    DetailActivity::class.java
                )
                toDetail.putExtra(DetailActivity.EXTRA_USER, data.login)
                startActivity(toDetail)
            }
        })

        binding.listFavorite.adapter = listUserAdapter
        binding.listFavorite.layoutManager = LinearLayoutManager(this)
    }
}