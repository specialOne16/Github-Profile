package com.jundapp.githubprofile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jundapp.githubprofile.AlarmReceiver
import com.jundapp.githubprofile.SharedPreferencesManager
import com.jundapp.githubprofile.databinding.ActivitySettingBinding

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

        binding.alarmSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
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