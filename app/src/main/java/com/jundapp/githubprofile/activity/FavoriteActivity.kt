package com.jundapp.githubprofile.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.adapter.UserListAdapter
import com.jundapp.githubprofile.databinding.ActivityFavoriteBinding
import com.jundapp.githubprofile.db.FavoriteHelper
import com.jundapp.githubprofile.helper.MappingHelper
import com.jundapp.githubprofile.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var listUserAdapter: UserListAdapter

    private lateinit var favHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favHelper = FavoriteHelper.getInstance(applicationContext)
        supportActionBar?.title = resources.getString(R.string.favorite_title)

        listUserAdapter = UserListAdapter(this, arrayListOf())
        binding.listFavorite.layoutManager = LinearLayoutManager(this)

        listUserAdapter.setOnItemClickCallback(object :
            UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val toDetail = Intent(
                    this@FavoriteActivity,
                    DetailActivity::class.java
                )
                toDetail.putExtra(DetailActivity.EXTRA_USER, data.login)
                toDetail.putExtra(DetailActivity.EXTRA_USER_ID, data.id)
                startActivity(toDetail)
            }
        })

        binding.listFavorite.adapter = listUserAdapter
        binding.listFavorite.layoutManager = LinearLayoutManager(this)
    }

    private fun loadFavoritesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            favHelper.open()
            val deferredFavorites = async(Dispatchers.IO) {
                val cursor = favHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFavorites.await()
            if (favorites.size > 0) {
                listUserAdapter.data.clear()
                listUserAdapter.data.addAll(favorites)
                listUserAdapter.notifyDataSetChanged()
                binding.noData.visibility = View.GONE
            } else {
                listUserAdapter.data.clear()
                listUserAdapter.notifyDataSetChanged()
                binding.noData.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favHelper.close()
    }

    override fun onResume() {
        super.onResume()
        loadFavoritesAsync()
    }
}