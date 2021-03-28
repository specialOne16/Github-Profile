package com.jundapp.githubprofile.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jundapp.githubprofile.UserData
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.User
import com.jundapp.githubprofile.adapter.UserListAdapter

class MainActivity : AppCompatActivity() {

    lateinit var listJob: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listJob = findViewById(R.id.listJob)
        listJob.setHasFixedSize(true)

        UserData.init(this)

        loadJobData()

    }

    private fun loadJobData() {

        listJob.layoutManager = LinearLayoutManager(this)
        val listJobAdapter = UserListAdapter(this, UserData.listData)
        listJob.adapter = listJobAdapter

        listJobAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val toDetail = Intent(this@MainActivity, DetailActivity::class.java)
                toDetail.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(toDetail)
            }
        })

    }

}
