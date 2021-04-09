package com.jundapp.consumerapp.favoritehelper

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.jundapp.githubprofile"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            private const val TABLE_NAME = "favoriteuser"
            const val LOGIN = "login"
            const val ID = "id" // ID DATABASE GITHUB
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri =
                Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME).build()
        }
    }
}