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
                " ${FavoriteColumns.ID} INTEGER NOT NULL," +
                " ${FavoriteColumns.LOGIN} VARCHAR(255) NOT NULL," +
                " ${FavoriteColumns.AVATAR_URL} VARCHAR(255))"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}