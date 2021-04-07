package com.jundapp.githubprofile.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.jundapp.githubprofile.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.jundapp.githubprofile.db.DatabaseContract.FavoriteColumns.Companion.ID
import com.jundapp.githubprofile.db.DatabaseContract.FavoriteColumns.Companion._ID
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavoriteHelper(context)
        }

        private lateinit var database: SQLiteDatabase
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    fun insert(values: ContentValues): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(_id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$_id'", null)
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }
}