package com.jundapp.githubprofile

import android.content.Context

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val PREFS_NAME = "setting_pref"
        private const val REMINDER = "reminder"

        private const val REMINDER_UNDEF = 0
        private const val REMINDER_ON = 1
        private const val REMINDER_OFF = 2

    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Boolean){
        val editor = preferences.edit()
        if(value) editor.putInt(REMINDER, REMINDER_ON)
        else editor.putInt(REMINDER, REMINDER_OFF)
        editor.apply()
    }
    fun getReminder(): Boolean? {
        return when(preferences.getInt(REMINDER, REMINDER_UNDEF)){
            REMINDER_ON -> true
            REMINDER_OFF -> false
            else -> null
        }
    }
}