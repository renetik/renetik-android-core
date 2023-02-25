package renetik.android.core.extensions.content

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.*
import android.content.ContextWrapper.MIDI_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.media.midi.MidiManager
import android.os.PowerManager
import android.view.inputmethod.InputMethodManager
import renetik.android.core.kotlin.className
import renetik.android.core.logging.CSLog.logError
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLogMessage.Companion.message

val Context.notifications get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
val Context.audioManager get() = getSystemService(AUDIO_SERVICE) as AudioManager
val Context.bluetoothService get() = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
val Context.inputService get() = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

val Context.midiManager: MidiManager? get() = getSystemService(MIDI_SERVICE) as? MidiManager
val Context.isMidiSupported: Boolean get() = midiManager != null
fun <T> Context.ifHasMidi(function: (MidiManager) -> T): T? = midiManager?.let { function(it) }

@SuppressLint("WakelockTimeout")
fun Context.wakeLock(levelAndFlags: Int): PowerManager.WakeLock {
    val lock = (getSystemService(POWER_SERVICE) as PowerManager)
        .newWakeLock(levelAndFlags, "wakeLock:${className}")
    lock.acquire()
    if (lock.isHeld) logInfo { message("Wake Lock held: $this") }
    else logError { message("Wake Lock not held: $this") }
    return lock
}

fun Context.startService(serviceClass: Class<out Service>) =
    startService(Intent(this, serviceClass))

inline fun <reified T : Service> Context.startService() =
    startService(Intent(this, T::class.java))

fun Context.stopService(serviceClass: Class<out Service>) =
    stopService(Intent(this, serviceClass))

inline fun <reified T : Service> Context.stopService() =
    stopService(Intent(this, T::class.java))
