package com.jundapp.githubprofile.db

import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.jundapp.githubprofile"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoriteuser"
            const val _ID = "_ID" // ID DATABASE SQLite
            const val LOGIN = "login"
            const val ID = "id" // ID DATABASE GITHUB
            const val AVATAR_URL = "avatar_url"
        }
    }
}