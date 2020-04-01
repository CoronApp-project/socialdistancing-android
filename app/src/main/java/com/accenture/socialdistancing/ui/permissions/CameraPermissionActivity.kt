package com.accenture.socialdistancing.ui.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.accenture.socialdistancing.R
import com.accenture.socialdistancing.ui.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_camera_permission.*

class CameraPermissionActivity: AppCompatActivity() {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1
    }

    private var isVerifyingScreen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_permission)

        btnPermissionNo.setOnClickListener {
            if(!isVerifyingScreen) setVerifyingScreen()
            else finish()
        }

        btnPermissionOk.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun setVerifyingScreen() {
        isVerifyingScreen = true
        tvPermission.text = getString(R.string.camera_permission_2)
        btnPermissionNo.text = getString(R.string.permission_no_2)
        btnPermissionOk.text = getString(R.string.permission_ok_2)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                } else {
                    // permission denied, boo!
                    setVerifyingScreen()
                }
                return
            }
        }
    }
}