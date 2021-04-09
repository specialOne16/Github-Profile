package com.jundapp.consumerapp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jundapp.consumerapp.adapter.UserListAdapter
import com.jundapp.consumerapp.databinding.ActivityMainBinding
import com.jundapp.consumerapp.favoritehelper.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.jundapp.consumerapp.favoritehelper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserListAdapter(this, arrayListOf())
        binding.listFavorite.layoutManager = LinearLayoutManager(this)
        binding.listFavorite.adapter = adapter
    }

    private fun loadFavoritesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFavorites = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFavorites.await()
            if (favorites.size > 0) {
                adapter.data.clear()
                adapter.data.addAll(favorites)
                adapter.notifyDataSetChanged()
                binding.noData.visibility = View.GONE
            } else {
                adapter.data.clear()
                adapter.notifyDataSetChanged()
                binding.noData.visibility = View.VISIBLE
            }
            Log.d("loading", favorites.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavoritesAsync()
    }

}