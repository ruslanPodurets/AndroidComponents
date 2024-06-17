package lib.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object CommonActions {

    /**
     * for current realization hardcoded in method type only works correctly in most cases
     * */
    @JvmStatic
    fun getVerifiedShareContentType(): String {
        return "message/rfc822"
    }

    /**
     * default file provider
     * */
    @JvmStatic
    fun getDefaultFileProvider(context: Context) : String = context.packageName + ".fileprovider"

    /**
     * dirty solution for share preview android to avoid
     * @link https://stackoverflow.com/questions/62323291/coulnt-sharing-file-requires-the-provider-be-exported-or-granturipermission
     * */
    @JvmStatic
    private fun allowToSystemReadUriPermission(context: Context, uri: Uri) {
        try {
            context.grantUriPermission("android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    private fun configureDefaultShareIntent(shareIntent: Intent, subject: String, text: String) {
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = getVerifiedShareContentType()
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    }

    @JvmStatic
    fun launchShareIntent(context: Context, title: String, shareIntent: Intent) {
        val chooserIntent = Intent.createChooser(shareIntent, title)
        chooserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (context !is Activity) {
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent)
    }

    @JvmStatic
    fun share(context: Context, authority: String, file: File, subject: String, text: String) {
        val uri = FileProvider.getUriForFile(context.applicationContext, authority, file)
        val shareIntent = Intent(Intent.ACTION_SEND)
        configureDefaultShareIntent(shareIntent, subject, text)
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        launchShareIntent(context, subject, shareIntent)
    }

    @JvmStatic
    fun share(context: Context, authority: String, files: List<File>, subject: String, text: String) {
        if (files.isEmpty()) {
            return
        }
        if (files.size == 1) {
            share(context, authority, files[0], subject, text)
            return
        }
        val appContext = context.applicationContext
        val uriFiles = ArrayList<Uri>()
        val contentResolver = appContext.contentResolver
        var fileType: String? = null
        for (file in files) {
            val uri = FileProvider.getUriForFile(appContext, authority, file)
            allowToSystemReadUriPermission(context, uri)
            uriFiles.add(uri)
            if (fileType == null) {
                fileType = contentResolver.getType(uri)
            }
        }

        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        configureDefaultShareIntent(shareIntent, subject, text)
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriFiles)
        launchShareIntent(context, subject, shareIntent)
    }


    @JvmStatic
    fun share(context: Context, file: File, subject: String, text: String) {
        share(context, getDefaultFileProvider(context), file, subject, text)
    }

    @JvmStatic
    fun share(context: Context, files: List<File>, subject: String, text: String) {
        share(context, getDefaultFileProvider(context), files, subject, text)
    }

}