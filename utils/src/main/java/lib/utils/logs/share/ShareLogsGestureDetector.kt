package lib.utils.logs.share

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import lib.utils.CommonActions
import lib.utils.logs.ILogger

class ShareLogsGestureDetector(private val logger: ILogger, context: Context) {

    private val gestureHandler = Handler(Looper.getMainLooper())
    private val appContext = context.applicationContext
    private val gestureDetector: GestureDetector

    init {
        gestureDetector =
            GestureDetector(appContext, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    postOpenShareLogs()
                    return true
                }

                override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                    postOpenShareLogs()
                    return true
                }
            })
    }

    fun onTouchEvent(ev: MotionEvent): Boolean {
        if (logger.isEnabled()) {
            return gestureDetector.onTouchEvent(ev)
        }
        return false
    }

    fun onStop() {
        clearPending()
    }

    private fun postOpenShareLogs() {
        clearPending()
        gestureHandler.postDelayed({
            processShareLogsImmediately()
        }, 500)
    }

    private fun processShareLogsImmediately() {
        try {
            clearPending()
            if (logger.isEnabled()) {
                val listLogFiles = logger.logFiles()
                if (listLogFiles.isNotEmpty()) {
                    CommonActions.share(
                        appContext,
                        listLogFiles,
                        "Send logs",
                        "Debug info for dev"
                    )
                }
            }
        } catch (e: Throwable) {
            logger.logError(
                ShareLogsGestureDetector::class.java.simpleName,
                "processShareLogsImmediately", e
            )
        }
    }

    private fun clearPending() {
        gestureHandler.removeCallbacksAndMessages(null)
    }

}