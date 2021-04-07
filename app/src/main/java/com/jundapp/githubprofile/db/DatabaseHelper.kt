package com.jundapp.githubprofile.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jundapp.githubprofile.db.DatabaseContract.FavoriteColumns
import com.jundapp.githubprofile.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "db_github_app"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " (${FavoriteColumns.ID} INTEGER NOT NULL," +
                " ${FavoriteColumns.LOGIN} VARCHAR(255) NOT NULL" +
                " ${FavoriteColumns.NODE_ID} VARCHAR(255)" +
                " ${FavoriteColumns.AVATAR_URL} VARCHAR(255)" +
                " ${FavoriteColumns.GRAVATAR_ID} VARCHAR(255)" +
                " ${FavoriteColumns.URL} VARCHAR(255)" +
                " ${FavoriteColumns.HTML_URL} VARCHAR(255)" +
                " ${FavoriteColumns.FOLLOWERS_URL} VARCHAR(255)" +
                " ${FavoriteColumns.FOLLOWING_URL} VARCHAR(255)" +
                " ${FavoriteColumns.GISTS_URL} VARCHAR(255)" +
                " ${FavoriteColumns.STARRED_URL} VARCHAR(255)" +
                " ${FavoriteColumns.SUBSCRIPTION_URL} VARCHAR(255)" +
                " ${FavoriteColumns.ORGANIZATION_URL} VARCHAR(255)" +
                " ${FavoriteColumns.REPOS_URL} VARCHAR(255)" +
                " ${FavoriteColumns.EVENTS_URL} VARCHAR(255)" +
                " ${FavoriteColumns.RECEIVED_EVENTS_URL} VARCHAR(255)" +
                " ${FavoriteColumns.TYPE} VARCHAR(255)" +
                " ${FavoriteColumns.SITE_ADMIN} VARCHAR(255)" +
                " ${FavoriteColumns.SCORE} FLOAT(24)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}