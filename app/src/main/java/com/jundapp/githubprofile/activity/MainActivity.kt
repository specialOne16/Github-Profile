package com.jundapp.githubprofile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jundapp.githubprofile.databinding.ActivityMainBinding
import com.jundapp.githubprofile.fragments.UserListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener { showList(binding.searchBar.text.toString()) }

    }

    private fun showList(keyword: String){
        val userListFragment = UserListFragment.newInstance("https://api.github.com/search/users?q=${keyword}")
        supportFragmentManager
            .beginTransaction()
            .replace(binding.userListContainer.id, userListFragment)
            .commit()
    }

}
