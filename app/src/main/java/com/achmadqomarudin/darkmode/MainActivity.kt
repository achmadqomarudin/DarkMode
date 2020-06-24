package com.achmadqomarudin.darkmode

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

const val FIRST_START = "FirstStart"
const val NIGHT_MODE = "NightMode"
const val PREF = "AppSettingsPrefs"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appSettingsPrefs: SharedPreferences = getSharedPreferences(PREF, 0)
        val isNightModeOn: Boolean = appSettingsPrefs.getBoolean(NIGHT_MODE, true)
        val isFirstStart: Boolean = appSettingsPrefs.getBoolean(FIRST_START,false)
        val editor: SharedPreferences.Editor = appSettingsPrefs.edit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && isFirstStart ){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else{
            when {
                isNightModeOn -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        btnSwitch.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean(FIRST_START, false)
                editor.putBoolean(NIGHT_MODE, false)
                editor.apply()
                //recreate activity to make changes visible
                recreate()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean(FIRST_START, false)
                editor.putBoolean(NIGHT_MODE, true)
                editor.apply()
                recreate()

            }
        }

        btnDetail.setOnClickListener {
            intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume(){
        super.onResume()
        btnSwitch.text = getString(R.string.strEnable)
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        Log.i(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
