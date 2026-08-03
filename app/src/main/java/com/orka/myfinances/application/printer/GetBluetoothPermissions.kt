package com.orka.myfinances.application.printer

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.getPermissions() {
    val permissions = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        permissions.add(Manifest.permission.BLUETOOTH_SCAN)
        permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
    } else {
        permissions.add(Manifest.permission.BLUETOOTH)
        permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val toRequest = permissions.filter {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }

    if (toRequest.isNotEmpty()) ActivityCompat.requestPermissions(this, toRequest.toTypedArray(), 100)
}