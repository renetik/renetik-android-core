package renetik.android.core.extensions.content

import android.app.Service
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.*
import android.content.ContextWrapper.MIDI_SERVICE
import android.content.Intent
import android.media.AudioManager
import android.media.midi.MidiManager
import android.view.inputmethod.InputMethodManager

val Context.audioService get() = getSystemService(AUDIO_SERVICE) as AudioManager
val Context.bluetoothService get() = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
val Context.inputService get() = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

val Context.midiService: MidiManager? get() = getSystemService(MIDI_SERVICE) as? MidiManager
val Context.isMidiSupported: Boolean get() = midiService != null
fun <T> Context.ifHasMidi(function: (MidiManager) -> T): T? = midiService?.let { function(it) }

fun Context.startService(serviceClass: Class<out Service>) =
    startService(Intent(this, serviceClass))

fun Context.stopService(serviceClass: Class<out Service>) =
    stopService(Intent(this, serviceClass))

