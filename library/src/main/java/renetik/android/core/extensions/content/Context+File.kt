package renetik.android.core.extensions.content

import android.content.Context
import java.io.File
import java.util.UUID

fun Context.temporaryFile(extension: String? = null): File =
    File.createTempFile(applicationLabel, extension?.let { ".$it" }, cacheDir)

fun Context.temporaryFolder(): File {
    val uniqueName = "temp_folder_${UUID.randomUUID()}"
    val tempFolder = File(cacheDir, uniqueName)
    if (!tempFolder.exists()) tempFolder.mkdir()
    return tempFolder
}