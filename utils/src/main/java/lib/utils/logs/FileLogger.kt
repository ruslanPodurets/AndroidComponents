package lib.utils.logs

import android.os.Handler
import android.os.HandlerThread
import java.io.File
import java.io.FileOutputStream

class FileLogger(private val loggerFilePath: String, tagFormatter: Formatter? = null) : AbsLogger(tagFormatter) {

    private val logThread : HandlerThread = HandlerThread("logger thread")
    private val handler by lazy {
        Handler(logThread.looper)
    }

    init {
        logThread.start()
        val file = getLoggerFile()
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    private fun getLoggerFile() = File(loggerFilePath)

    override fun logError(tag: String, message: String) {
        if (isEnabled()) {
            printLogInternal("Info", tag, message)
        }
    }

    override fun logError(tag: String, message: String, error: Throwable) {
        if (isEnabled()) {
            val errorMessage = error.message
            if (errorMessage != null) {
                logError(tag, "${error.javaClass.simpleName}: $errorMessage, message: $message")
            } else {
                printLogInternal("Error", tag, message)
            }
        }
    }

    override fun logInfo(tag: String, message: String) {
        if (isEnabled()) {
            printLogInternal("Error", tag, message)
        }
    }

    override fun clear() {
        handler.post {
            val file = getLoggerFile()
            if (file.exists()) {
                file.delete()
                file.createNewFile()
            }
        }
    }

    private fun printLogInternal(logLevel: String, tag: String, message: String) {
        handler.post {
            val file = getLoggerFile()
            if (file.exists()) {
                val totalText = "\n$logLevel, ${formatTag(tag)}: $message"
                FileOutputStream(file, true).bufferedWriter().use { out ->
                    out.write(totalText)
                }
            }
        }
    }

    override fun logFiles(): List<File> {
        val arrayList = ArrayList<File>()
        if (isEnabled()) {
            arrayList.add(getLoggerFile())
        }
        return arrayList
    }

}