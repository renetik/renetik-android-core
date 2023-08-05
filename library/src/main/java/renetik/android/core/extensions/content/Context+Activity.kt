package renetik.android.core.extensions.content

import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

inline fun <reified Activity : AppCompatActivity> Context.startActivity(
    extras: Map<String, String> = emptyMap()
) {
    val activityClass: KClass<Activity> = Activity::class
    startActivity(activityClass, extras)
}

fun <Activity : AppCompatActivity> Context.startActivity(
    activityClass: KClass<out Activity>, extras: Map<String, String> = emptyMap()
) {
    val intent = Intent(this, activityClass)
    for ((key, value) in extras) intent.putExtra(key, value)
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}