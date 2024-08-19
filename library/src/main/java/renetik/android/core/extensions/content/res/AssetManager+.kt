package renetik.android.core.extensions.content.res

import android.content.res.AssetManager
import renetik.android.core.java.io.copy
import renetik.android.core.java.io.createFileAndDirs
import renetik.android.core.kotlin.primitives.iterator
import java.io.File

fun AssetManager.copyFilesToDir(path: String, targetDir: File, overwrite: Boolean = false) {
    for (file in list(path).iterator) {
        val target = File(targetDir, file)
        if (overwrite || !target.exists())
            target.createFileAndDirs().outputStream().use {
                open("$path/$file".replace("//", "/")).copy(it)
            }
    }
}

fun AssetManager.copyFilesToDir2(path: String, targetDir: File, overwrite: Boolean = false) {
    val items = list(path)
    for (item in items ?: emptyArray()) {
        val assetPath = "$path/$item"
        val targetPath = File(targetDir, item)
        if ('.' in item) {
            if (overwrite || !targetPath.exists()) {
                targetPath.createFileAndDirs().outputStream().use {
                    open(assetPath.replace("//", "/")).copy(it)
                }
            }
        } else {
            val subDir = File(targetDir, item)
            if (!subDir.exists()) subDir.mkdirs()
            copyFilesToDir2(assetPath, subDir, overwrite)
        }
    }
}

fun AssetManager.isFile(path: String): Boolean = !isDir(path)

fun AssetManager.isDir(path: String): Boolean = '.' !in path //sadly...
//    runCatching {
//    openFd(path).let { it.length == AssetFileDescriptor.UNKNOWN_LENGTH && it.declaredLength < 0 }
//}.getOrNull() ?: false

fun AssetManager.copyFileToDir(assetPath: String, folder: File, overwrite: Boolean = false) {
    val target = File(folder, File(assetPath).name)
    if (overwrite || !target.exists())
        target.createFileAndDirs().outputStream().use {
            open(assetPath.replace("//", "/")).copy(it)
        }
}