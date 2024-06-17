package lib.utils.logs

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import lib.utils.logs.Formatter
import java.util.Locale

class LogTagDateFormatter(dateFormat: String) : Formatter {

    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(dateFormat, Locale.UK)
    private val calendar: Calendar = Calendar.getInstance()

    override fun format(text: String): String {
        val formattedTime = simpleDateFormat.format(calendar.time)
        return "$formattedTime, $text"
    }
}