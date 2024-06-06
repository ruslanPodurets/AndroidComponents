package lib.notifications

import android.content.Context

interface INotificationChannelSource {

    fun channelInfo(channelId: String, context: Context) : ChannelInfo

}