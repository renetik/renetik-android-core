package renetik.android.core.extensions.content

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

inline fun <reified Activity : AppCompatActivity> Context.startActivity() {
    startActivity(Intent(this, Activity::class.java))
}