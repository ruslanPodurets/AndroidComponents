package lib.utils.logs

import android.content.Context
import java.io.File

interface ILogger {

    fun isEnabled(): Boolean

    fun setEnabled(isEnabled: Boolean)

    fun init(context: Context)

    fun defaultTag(): String

    fun logError(tag: String = defaultTag(), message: String)

    fun logError(tag: String = defaultTag(), message: String, error: Throwable)

    fun logInfo(tag: String = defaultTag(), message: String)

    fun clear()

    fun logFiles(): List<File>

}