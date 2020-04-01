package com.accenture.socialdistancing.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.accenture.socialdistancing.MainActivity
import com.accenture.socialdistancing.R


class SplashActivity : AppCompatActivity() {
    companion object {
        private const val PREFERENCES_NAME = "preferences"
        private const val FIRST_TIME_PREFERENCE = "first_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val isFirstTime = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).getBoolean(FIRST_TIME_PREFERENCE, true)
            if(isFirstTime) {
                //TODO: Go to tutorial
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }, 3000)
    }
}