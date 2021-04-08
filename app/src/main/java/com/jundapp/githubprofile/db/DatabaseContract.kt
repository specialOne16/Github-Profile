package com.jundapp.githubprofile.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    val AUTHORITY = "com.jundapp.githubprofile"
    val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoriteuser"
            const val _ID = "_ID" // ID DATABASE SQLite
            const val LOGIN = "login"
            const val ID = "id" // ID DATABASE GITHUB
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri =
                Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME).build()
        }
    }
}