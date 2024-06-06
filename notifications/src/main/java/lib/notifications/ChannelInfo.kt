package lib.notifications

import android.app.NotificationManager

class ChannelInfo(val channelId: String) {
    var channelName: String = ""
    var channelDescription: String = ""
    private var importance: Int = NotificationManager.IMPORTANCE_DEFAULT

    fun importance() = importance

    fun withHighImportance() {
        importance = NotificationManager.IMPORTANCE_HIGH
    }
}