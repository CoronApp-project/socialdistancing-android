package com.accenture.socialdistancing

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.accenture.socialdistancing.utils.ForegroundCheckTask
import android.content.Intent
import android.os.CountDownTimer
import android.os.Build
import android.app.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val RSSI_MINIMUM_ACCEPTABLE_VALUE = -70
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2
        private const val BLUETOOTH_ENABLE_REQUEST_CODE = 3
        private const val NOTIFICATION_CHANNEL_ID = "coronavirus"
    }

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var shouldNotify = true
    private val countDown = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            shouldNotify = true
        }

        override fun onTick(millisUntilFinished: Long) {
            shouldNotify = false
        }
    }

    private val bleListener = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            val detectedDevice = result?.device
            val rssi = result?.rssi

            if (rssi != null && rssi >= RSSI_MINIMUM_ACCEPTABLE_VALUE) {
                /*
                    -70 is minimum acceptable value
                    See https://es.wikipedia.org/wiki/Indicador_de_fuerza_de_la_señal_recibida
                 */

                Log.d("bleTest", "rssi: $rssi")
                if(shouldNotify) {
                    countDown.start()
                    if(detectedDevice?.name != null && detectedDevice.name != "null") notifySocialApproach(detectedDevice.name)
                    else if(detectedDevice?.address != null) notifySocialApproach(detectedDevice.address)
                    else notifySocialApproach(getString(R.string.unknown_device))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_aura, R.id.navigation_trusted_sources
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if(!bluetoothAdapter.isEnabled) {
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BLUETOOTH_ENABLE_REQUEST_CODE)
        } else {
            checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {
        when (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PackageManager.PERMISSION_GRANTED -> bluetoothAdapter.bluetoothLeScanner.startScan(bleListener)
            else -> requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun notifySocialApproach(detectedDeviceName: String) {
        val isAppInForeground = ForegroundCheckTask().execute(this).get()

        if(isAppInForeground) {
            Log.d("bleTest", "notifySocialApproach dialog")
            val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setIcon(R.mipmap.ic_launcher_round)
            alertDialog.setTitle(getString(R.string.app_name))
            alertDialog.setMessage(getString(R.string.social_approach_alert) + detectedDeviceName)
            alertDialog.show()
        } else {
            Log.d("bleTest", "notifySocialApproach push")

            val notificationBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            val ii = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, ii, 0)

            val bigText = NotificationCompat.BigTextStyle()
            bigText.bigText(getString(R.string.social_approach_alert) + detectedDeviceName)
            bigText.setBigContentTitle(getString(R.string.app_name))
            bigText.setSummaryText(detectedDeviceName)

            notificationBuilder.setContentIntent(pendingIntent)
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
            notificationBuilder.setContentTitle(getString(R.string.app_name))
            notificationBuilder.setContentText(getString(R.string.social_approach_alert) + detectedDeviceName)
            notificationBuilder.priority = Notification.PRIORITY_MAX
            notificationBuilder.setStyle(bigText)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = NOTIFICATION_CHANNEL_ID
                val channel = NotificationChannel(channelId, getString(R.string.channel_id_notification_description),
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
                notificationBuilder.setChannelId(channelId)
            }

            notificationManager.notify(0, notificationBuilder.build())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    bluetoothAdapter.bluetoothLeScanner.startScan(bleListener)
                } else Toast.makeText(this, getString(R.string.location_permission_denied), Toast.LENGTH_LONG).show()
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            BLUETOOTH_ENABLE_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK) checkLocationPermission()
                else Toast.makeText(this, getString(R.string.location_permission_denied), Toast.LENGTH_LONG).show()
            }
        }
    }
}