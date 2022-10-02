package com.example.tugasbesarpbp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent!!.getStringExtra("toastMessage")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()


    }
}