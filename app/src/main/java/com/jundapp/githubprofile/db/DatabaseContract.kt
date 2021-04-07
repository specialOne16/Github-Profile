package com.jundapp.githubprofile.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoriteuser"
            const val _ID = "_ID" // ID DATABASE SQLite
            const val LOGIN = "login"
            const val ID = "id" // ID DATABASE GITHUB
            const val NODE_ID = "node_id"
            const val AVATAR_URL = "avatar_url"
            const val GRAVATAR_ID = "gravatar_id"
            const val URL = "url"
            const val HTML_URL = "html_url"
            const val FOLLOWERS_URL = "followers_url"
            const val FOLLOWING_URL = "following_url"
            const val GISTS_URL = "gists_url"
            const val STARRED_URL = "starred_url"
            const val SUBSCRIPTION_URL = "subscriptions_url"
            const val ORGANIZATION_URL = "organizations_url"
            const val REPOS_URL = "repos_url"
            const val EVENTS_URL = "events_url"
            const val RECEIVED_EVENTS_URL = "received_events_url"
            const val TYPE = "type"
            const val SITE_ADMIN = "site_admin"
            const val SCORE = "score"
        }
    }
}