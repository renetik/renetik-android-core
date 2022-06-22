package renetik.android.core.extensions.content

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlin.reflect.KClass

fun Intent.toOpenUrl(url: String) = apply {
    action = Intent.ACTION_VIEW
    data = Uri.parse(url)
}

val Intent.asString: String
    get() {
        val string = StringBuilder(toString())
        extras?.let {
            string.append(" Extras: ")
            for (key in it.keySet()) {
                val info = it.get(key)
                string.append("$key $info (${info?.javaClass?.name ?: ""})")
            }
        }
        return string.toString()
    }


fun Intent(context: Context, kClass: KClass<*>) = Intent(context, kClass.java)
fun Intent(action: String, type: String) = Intent(action).also { it.type = type }
fun Intent(action: String, category: String, type: String) = Intent(action).also {
    it.addCategory(category)
    it.type = type
}