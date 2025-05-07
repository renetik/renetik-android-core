package renetik.android.core.extensions.content.res

import android.content.res.AssetManager
import renetik.android.core.logging.CSLog.logError
import java.io.File

fun AssetManager.copyContentsToDir(
    path: String, targetDir: File,
    overwrite: Boolean = false, recurse: Boolean = true
) {
    if (targetDir.exists() && targetDir.isFile) {
        logError("targetDir is file")
        return
    }
    targetDir.mkdirs()
    val children = list(path) ?: emptyArray()
    for (name in children) {
        val assetPath = if (path.isEmpty()) name else "$path/$name"
        val dest = File(targetDir, name)
        if (isDir(assetPath)) {
            if (overwrite || !dest.exists()) dest.mkdirs()
            if (recurse) copyContentsToDir(assetPath, dest, overwrite, recurse)
        } else if (overwrite || !dest.exists()) {
            dest.parentFile?.mkdirs()
            open(assetPath).use { input ->
                dest.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}

fun AssetManager.isDir(path: String): Boolean =
    runCatching { list(path)?.isNotEmpty() == true }.getOrDefault(false)

fun AssetManager.copyFileToDir(
    assetPath: String, folder: File,
    overwrite: Boolean = false
) = copyContentsToDir(
    path = assetPath.substringBeforeLast('/'),
    targetDir = folder, overwrite = overwrite, recurse = false
)