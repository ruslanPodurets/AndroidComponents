package lib.utils.disk

import android.content.Context
import java.io.File

class DeviceStorage {

    fun availableFilesDir(context: Context): File {
        return externalFilesDir(context) ?: internalFilesDir(context)
    }

    fun internalFilesDir(context: Context): File {
        return context.filesDir
    }


    fun externalFilesDir(context: Context): File? {
        val externalFilesDir = context.getExternalFilesDir(null)
        if (externalFilesDir != null && externalFilesDir.exists()) {
            return externalFilesDir
        }
        return null
    }

}