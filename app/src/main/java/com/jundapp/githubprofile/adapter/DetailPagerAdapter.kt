package com.jundapp.githubprofile.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jundapp.githubprofile.fragments.UserListFragment

class DetailPagerAdapter(activity: AppCompatActivity, val uname: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = UserListFragment.newInstance("https://api.github.com/users/${uname}/followers")
            1 -> fragment = UserListFragment.newInstance("https://api.github.com/users/${uname}/following")
        }
        return  fragment as Fragment
    }

}