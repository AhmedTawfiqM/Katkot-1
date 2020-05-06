package com.ebdaa.katkot.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews

import androidx.core.app.NotificationCompat

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.main_fragments.FragmentPeriod
import com.ebdaa.katkot.ui.mainactivity.MainActivity

import java.util.Random

object NotificationHelper {


    //..
    fun showNotiifcation(packageName: Context) {


        val contentView = RemoteViews(packageName.packageName, R.layout.custom_notify_alert)
        contentView.setImageViewResource(R.id.image, R.drawable.kaktot_yellow)
        // contentView.setTextViewText(R.id.title, "Custom notification");
        //contentView.setTextViewText(R.id.text, "This is a custom layout");

        //
        val manager = packageName.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "my_channel_0-1"

        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = "cancel description"
            notificationChannel.enableLights(true)

            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)

            manager.createNotificationChannel(notificationChannel)
        }
        //........
        val intent = Intent(packageName, MainActivity::class.java)


        val pendingIntent = PendingIntent.getActivity(packageName, Random().nextInt(100), intent,
                PendingIntent.FLAG_ONE_SHOT)

        //        long when = System.currentTimeMillis();
        //        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //
        //        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(packageName, NOTIFICATION_CHANNEL_ID);
        //        mNotifyBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000});
        //        boolean lollipop = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        //        if (lollipop) {
        //
        //            mNotifyBuilder = new NotificationCompat.Builder(packageName, NOTIFICATION_CHANNEL_ID)
        //                    .setContentTitle(packageName.getString(R.string.app_name))
        //                    .setStyle(
        //                            new NotificationCompat.BigTextStyle()
        //                                    .bigText(""))
        //                    .setContentText("")
        //                    .setSmallIcon(R.mipmap.ic_launcher_round)
        //                    .setColor(Color.TRANSPARENT)
        //                    .setLargeIcon(
        //                            BitmapFactory.decodeResource(
        //                                    packageName.getResources(),
        //                                    R.mipmap.ic_launcher))
        //                    .setSound(defaultSoundUri)
        //                    .setContentIntent(pendingIntent);
        //
        //        } else {
        //
        //            mNotifyBuilder = new NotificationCompat.Builder(packageName, NOTIFICATION_CHANNEL_ID)
        //                    .setStyle(
        //                            new NotificationCompat.BigTextStyle()
        //                                    .bigText(""))
        //                    .setContentText("")
        //                    .setContentTitle(packageName.getString(R.string.app_name)).setContentText("")
        //                    .setSmallIcon(R.mipmap.ic_launcher_round)
        //                    .setSound(defaultSoundUri)
        //                    .setContentIntent(pendingIntent);
        //
        //        }

        //.....
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mBuilder = NotificationCompat.Builder(packageName, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.kaktot_orange)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setContent(contentView)


        val notification = mBuilder.build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
        notification.defaults = Notification.DEFAULT_SOUND
        notification.defaults = Notification.DEFAULT_VIBRATE

        manager.notify(Random().nextInt(100) /* ID of notification */, notification)


    }

    fun showFirebaseNotify(context: Context, title: String, text: String) {


        //
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "my_channel_1-22"

        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = "cancel description"
            notificationChannel.enableLights(true)

            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)

            manager.createNotificationChannel(notificationChannel)
        }
        //........
        //........
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(context, Random().nextInt(100), intent,
                PendingIntent.FLAG_ONE_SHOT)

        //.....
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val mBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.kaktot_orange)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(title)

        //Any new notification will replace the previous one since you use 0 as an id
        manager.notify(Random().nextInt(100) /* ID of notification */, mBuilder.build())
    }
}
