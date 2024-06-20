package lib.notifications

import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import java.util.Random

class NotificationParams(val notificationId: Int, @DrawableRes var smallIcon: Int) {

    var title: CharSequence? = null
    var contentText: CharSequence? = null
    private var priority: Int = NotificationCompat.PRIORITY_DEFAULT
    var cancelable: Boolean = true
    var vibroPattern: LongArray? = null
    var isSoundAllowed: Boolean = true

    fun priority() = priority

    fun withHighPriority() {
        priority = NotificationCompat.PRIORITY_HIGH
    }

    companion object {

        private fun randomInt(from: Int, to: Int): Int {
            return Random().nextInt(to - from) + from
        }

        private fun randomInt(): Int {
            return randomInt(1, Int.MAX_VALUE)
        }

        @JvmStatic
        fun byRandomId(@DrawableRes smallIcon: Int): NotificationParams {
            return byId(randomInt(), smallIcon)
        }

        @JvmStatic
        fun byId(id: Int, @DrawableRes smallIcon: Int): NotificationParams {
            return NotificationParams(id, smallIcon)
        }

    }

}