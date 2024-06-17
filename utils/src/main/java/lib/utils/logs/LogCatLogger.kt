package lib.utils.logs

import android.util.Log
import lib.utils.logs.AbsLogger
import lib.utils.logs.Formatter

class LogCatLogger(tagFormatter: Formatter? = null) : AbsLogger(tagFormatter) {

    override fun logError(tag: String, message: String) {
        if (isEnabled()) {
            Log.e(formatTag(tag), message)
        }
    }

    override fun logError(tag: String, message: String, error: Throwable) {
        if (isEnabled()) {
            Log.e(formatTag(tag), message, error)
        }
    }

    override fun logInfo(tag: String, message: String) {
        if (isEnabled()) {
            Log.i(formatTag(tag), message)
        }
    }
}