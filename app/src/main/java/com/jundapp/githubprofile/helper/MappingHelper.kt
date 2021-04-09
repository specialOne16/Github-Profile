package com.jundapp.githubprofile.helper

import android.database.Cursor
import com.jundapp.githubprofile.db.DatabaseContract
import com.jundapp.githubprofile.models.UserModel

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserModel> {
        val notesList = ArrayList<UserModel>()
        notesCursor?.apply {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN))
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
                val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR_URL))
                notesList.add(UserModel(login, id, avatarUrl))
            }
        }
        return notesList
    }
}