package com.jundapp.githubprofile.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jundapp.githubprofile.services.AlarmReceiver
import com.jundapp.githubprofile.helper.SharedPreferencesManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefManager = SharedPreferencesManager(this)
        if(sharedPrefManager.getReminder() == null) {
            sharedPrefManager.setReminder(true)
            AlarmReceiver().setReminderAt9AM(this)
        }

        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()

    }
}