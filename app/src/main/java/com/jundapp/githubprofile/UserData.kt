package com.jundapp.githubprofile

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object UserData {
    private lateinit var names: List<String>
    private lateinit var userNames: List<String>
    private lateinit var location: List<String>
    private lateinit var repository: List<Int>
    private lateinit var company: List<String>
    private lateinit var followers: List<Int>
    private lateinit var following: List<Int>
    private lateinit var avatars: List<Int>

    fun init(context: Context) {
        names = context.resources.getStringArray(R.array.name).toList()
        userNames = context.resources.getStringArray(R.array.username).toList()
        location = context.resources.getStringArray(R.array.location).toList()
        repository = context.resources.getIntArray(R.array.repository).toList()
        company = context.resources.getStringArray(R.array.company).toList()
        followers = context.resources.getIntArray(R.array.followers).toList()
        following = context.resources.getIntArray(R.array.following).toList()
        avatars = listOf(
            R.drawable.user1,
            R.drawable.user2,
            R.drawable.user3,
            R.drawable.user4,
            R.drawable.user5,
            R.drawable.user6,
            R.drawable.user7,
            R.drawable.user8,
            R.drawable.user9,
            R.drawable.user10
        )
    }


    val listData: ArrayList<User>
        get() {
            val list = arrayListOf<User>()
            for (position in names.indices) {
                val user = User(
                    names[position],
                    userNames[position],
                    location[position],
                    repository[position],
                    company[position],
                    followers[position],
                    following[position],
                    avatars[position]
                )
                list.add(user)
            }
            return list
        }
}