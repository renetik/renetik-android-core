package renetik.android.core.extensions.content.res

import android.content.res.AssetManager
import renetik.android.core.logging.CSLog.logError
import java.io.File

fun AssetManager.copyPathToDir(
    item: String, targetDir: File,
    overwrite: Boolean = true
) {
    val item = item.trimEnd('/')
    if (targetDir.exists() && targetDir.isFile) {
        logError("targetDir is file")
        return
    }
    targetDir.mkdirs()
    if (isFile(item)) {
        val name = item.substringAfterLast('/')
        val dest = File(targetDir, name)
        if (overwrite || !dest.exists()) copyFile(item, dest)
        return
    }
    list(item)?.forEach { name ->
        val path = if (item.isEmpty()) name else "$item/$name"
        val dest = File(targetDir, name)
        if (isDir(path)) {
            if (overwrite || !dest.exists()) dest.mkdirs()
            copyPathToDir(path, dest, overwrite)
        } else if (overwrite || !dest.exists()) copyFile(path, dest)
    }
}

private fun AssetManager.copyFile(path: String, dest: File) {
    dest.parentFile?.mkdirs()
    open(path).use { input ->
        dest.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun AssetManager.isFile(path: String): Boolean =
    path.isNotEmpty() &&
            runCatching { open(path).close(); true }.getOrDefault(false)

fun AssetManager.isDir(path: String): Boolean = !isFile(path)