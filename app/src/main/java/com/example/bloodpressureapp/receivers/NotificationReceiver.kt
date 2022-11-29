package com.example.bloodpressureapp.receivers


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.activities.MainActivity
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.buildMinVersionO
import kotlin.random.Random

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val groupLayout = listOf(
            Pair(R.layout.layout_notification_small_1, R.layout.layout_notification_expand_1),
            Pair(R.layout.layout_notification_small_2, R.layout.layout_notification_expand_2),
            Pair(R.layout.layout_notification_small_3, R.layout.layout_notification_expand_3),
            Pair(R.layout.layout_notification_small_4, R.layout.layout_notification_expand_4),
            Pair(R.layout.layout_notification_small_5, R.layout.layout_notification_expand_5),
            Pair(R.layout.layout_notification_small_6, R.layout.layout_notification_expand_6),
            Pair(R.layout.layout_notification_small_7, R.layout.layout_notification_expand_7),
            Pair(R.layout.layout_notification_small_8, R.layout.layout_notification_expand_8),
            Pair(R.layout.layout_notification_small_9, R.layout.layout_notification_expand_9),
            Pair(R.layout.layout_notification_small_10, R.layout.layout_notification_expand_10),
            Pair(R.layout.layout_notification_small_11, R.layout.layout_notification_expand_11),
            Pair(R.layout.layout_notification_small_12, R.layout.layout_notification_expand_12),

            )
        if (buildMinVersionO()) {
            val channel = NotificationChannel(
                Constant.CHANNEL_ID,
                Constant.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val index = Random.nextInt(0, 11)

        val remoteViews = RemoteViews(context.packageName, groupLayout[index].first)
        val remoteViewsExpand = RemoteViews(context.packageName, groupLayout[index].second)

        val intent = Intent(context,MainActivity::class.java)

        intent.putExtra(Constant.KEY_NOTIFY_POS,index)
        val pendingIntent =PendingIntent.getActivity(
            context,
            200,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_last_star_active)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(remoteViews).setAutoCancel(true).setShowWhen(false)
            .setCustomBigContentView(remoteViewsExpand)
            .setContentIntent(pendingIntent)
            .setOngoing(false).priority = NotificationCompat.PRIORITY_HIGH

        // Get the Notification manager service
        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate an Id for each notification
        val id = System.currentTimeMillis() / 1000

        // Show a notification
        am.notify(id.toInt(), builder.build())
        Log.d("TAG", "lamoooo: ")
    }
}