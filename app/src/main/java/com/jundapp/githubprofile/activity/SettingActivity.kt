package com.jundapp.githubprofile.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jundapp.githubprofile.databinding.ActivitySettingBinding
import com.jundapp.githubprofile.helper.SharedPreferencesManager
import com.jundapp.githubprofile.services.AlarmReceiver

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()

        val sharedPrefManager = SharedPreferencesManager(this)
        binding.alarmSwitch.isChecked = sharedPrefManager.getReminder() ?: false

        binding.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                sharedPrefManager.setReminder(true)
                alarmReceiver.setReminderAt9AM(this)
            }else{
                sharedPrefManager.setReminder(false)
                alarmReceiver.cancelReminderAt9AM(this)
            }
        }

    }
}