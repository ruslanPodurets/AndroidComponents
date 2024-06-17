package lib.utils.logs

import android.content.Context
import java.io.File

abstract class AbsLogger(private var tagFormatter: Formatter?) : ILogger {

    private var isEnabled = false

    private val defaultTag: String by lazy {
        javaClass.simpleName
    }

    override fun isEnabled(): Boolean = isEnabled

    override fun setEnabled(isEnabled: Boolean) {
        this.isEnabled = isEnabled
    }

    override fun init(context: Context) {

    }

    override fun defaultTag(): String = defaultTag

    fun formatTag(tag: String): String {
        return tagFormatter?.format(tag) ?: tag
    }

    override fun clear() {

    }

    override fun logFiles(): List<File> = ArrayList()
}