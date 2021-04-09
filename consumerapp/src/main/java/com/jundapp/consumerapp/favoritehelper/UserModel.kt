package com.jundapp.consumerapp.favoritehelper

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val login: String,
    val id: Int,
    val avatar_url: String?,
) : Parcelable
