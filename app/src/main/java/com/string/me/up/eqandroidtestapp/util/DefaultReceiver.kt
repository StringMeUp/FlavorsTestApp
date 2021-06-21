package com.string.me.up.eqandroidtestapp.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.string.me.up.eqandroidtestapp.R

class DefaultReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, R.string.download_completed, Toast.LENGTH_SHORT).show()
    }
}