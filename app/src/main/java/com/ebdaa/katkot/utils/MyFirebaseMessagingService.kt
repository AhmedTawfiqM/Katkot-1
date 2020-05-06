package com.ebdaa.katkot.utils

import android.app.Notification
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import java.util.Objects


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        super.onMessageReceived(remoteMessage)
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            try {
                val title =remoteMessage.notification?.getTitle()
                val body = remoteMessage.notification!!.body

                title?.let { body?.let { it1 -> NotificationHelper.showFirebaseNotify(applicationContext, it, it1) } }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    companion object {


        private val TAG = "FNotifi"
    }

}
