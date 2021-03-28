package com.jundapp.githubprofile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String = "Jundullah",
    var userName: String = "specialOne16",
    var location: String = "Bandung",
    var repository: Int = 0,
    var company: String = "Institut Teknologi Bandung",
    var follower: Int = 0,
    var following: Int = 0,
    var avatar: Int = R.drawable.user2,
) : Parcelable