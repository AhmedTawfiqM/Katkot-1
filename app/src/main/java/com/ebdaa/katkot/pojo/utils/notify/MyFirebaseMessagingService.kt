package com.ebdaasoft.doctorapp_doctors.pojo.utils.notify

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ebdaasoft.doctorapp_doctors.pojo.utils.SharedPref
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


@SuppressLint("Registered")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        getSharedPreferences("_", MODE_PRIVATE).edit().putString(SharedPref.TOKEN_ID, token)
            .apply();
        Log.d("newToken1:", token)
    }


}