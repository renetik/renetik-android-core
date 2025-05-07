package renetik.android.core.extensions.content.res

import android.content.res.AssetManager
import renetik.android.core.logging.CSLog.logError
import java.io.File

fun AssetManager.copyPathToDir(
    path: String, targetDir: File,
    overwrite: Boolean = true, recurse: Boolean = true
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
            if (recurse) copyPathToDir(assetPath, dest, overwrite, recurse)
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