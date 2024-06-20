package lib.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationVisor {

    private var notificationManager: NotificationManager? = null

    private fun notificationManager(context: Context): NotificationManager {
        val notificationManagerLocal = notificationManager
        if (notificationManagerLocal != null) {
            return notificationManagerLocal
        }
        val notificationMgr: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager = notificationMgr
        return notificationMgr
    }

    fun showNotification(channelId: String, context: Context, params: NotificationParams, channelSource: INotificationChannelSource) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (!isChannelExists(context, channelId)) {
            createNotificationChannelInternal(
                context,
                channelSource.channelInfo(channelId, context)
            )
        }
        val builder = NotificationCompat.Builder(context, channelId)
        if(params.smallIcon != 0){
            builder.setSmallIcon(params.smallIcon)
        }
        if (params.vibroPattern != null) {
            builder.setVibrate(params.vibroPattern)
        } else if (!params.isSoundAllowed) {
            builder.setSilent(true)
        }
        builder.setContentTitle(params.title)
        builder.setContentText(params.contentText)
        builder.priority = params.priority()
        builder.setAutoCancel(params.cancelable)
        NotificationManagerCompat.from(context).notify(params.notificationId, builder.build())
    }

    fun createNotificationChannel(context: Context, channelInfo: ChannelInfo) {
        if (!isChannelExists(context, channelInfo.channelId)) {
            createNotificationChannelInternal(context, channelInfo)
        }
    }

    private fun isChannelExists(context: Context, channelId: String): Boolean {
        return notificationManager(context).getNotificationChannel(channelId) != null
    }

    private fun createNotificationChannelInternal(context: Context, channelInfo: ChannelInfo) {
        val channel = NotificationChannel(
            channelInfo.channelId,
            channelInfo.channelName,
            channelInfo.importance()
        )
        channel.description = channelInfo.channelDescription
        notificationManager(context).createNotificationChannel(channel)
    }


}