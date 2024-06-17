package lib.utils.logs

import android.content.Context
import java.io.File

class Logger(private val loggersArray: Array<ILogger>) : AbsLogger(null) {

    override fun init(context: Context) {
        super.init(context)
        for (logger in loggersArray) {
            logger.init(context)
        }
    }

    override fun setEnabled(isEnabled: Boolean) {
        super.setEnabled(isEnabled)
        for (logger in loggersArray) {
            logger.setEnabled(isEnabled)
        }
    }

    override fun logError(tag: String, message: String) {
        if (isEnabled()) {
            for (logger in loggersArray) {
                logger.logError(tag, message)
            }
        }
    }

    override fun logError(tag: String, message: String, error: Throwable) {
        if (isEnabled()) {
            for (logger in loggersArray) {
                logger.logError(tag, message, error)
            }
        }
    }

    override fun logInfo(tag: String, message: String) {
        if (isEnabled()) {
            for (logger in loggersArray) {
                logger.logInfo(tag, message)
            }
        }
    }

    override fun logFiles(): List<File> {
        val files = ArrayList<File>()
        for (logger in loggersArray) {
            files.addAll(logger.logFiles())
        }
        return files
    }

    companion object {

        fun logCatLogger(): Array<ILogger> = arrayOf(LogCatLogger())

        fun init(
            context: Context,
            loggersArray: Array<ILogger> = logCatLogger()
        ) : ILogger {
            val loggerLocal = Logger(loggersArray)
            loggerLocal.init(context)
            loggerLocal.setEnabled(true)
            return loggerLocal
        }

    }


}