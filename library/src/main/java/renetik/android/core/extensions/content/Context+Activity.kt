package renetik.android.core.extensions.content

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

inline fun <reified Activity : AppCompatActivity>
        Context.startActivity(extras: Map<String, String> = emptyMap()) {
    val intent = Intent(this, Activity::class.java)
    for ((key, value) in extras) intent.putExtra(key, value)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent)
}