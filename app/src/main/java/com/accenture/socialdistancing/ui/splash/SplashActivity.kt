package com.accenture.socialdistancing.ui.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.accenture.socialdistancing.HomeActivity
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.ui.onboarding.OnBoardingActivity
import com.accenture.socialdistancing.ui.permissions.CameraPermissionActivity


class SplashActivity : AppCompatActivity() {
    companion object {
        private const val PREFERENCES_NAME = "preferences"
        private const val FIRST_TIME_PREFERENCE = "first_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            checkCameraPermission()
        }, 3000)
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, CameraPermissionActivity::class.java)
            startActivity(intent)
        } else {
            Log.i(getString(R.string.app_name), "camera permission is granted")
            checkFirstTimeInApp()
        }

        finish()
    }

    private fun checkFirstTimeInApp() {
        val isFirstTime = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).getBoolean(FIRST_TIME_PREFERENCE, true)
        if(isFirstTime) {
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}