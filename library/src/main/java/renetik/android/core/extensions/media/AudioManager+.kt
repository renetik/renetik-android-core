package renetik.android.core.extensions.media

import android.media.AudioManager
import android.media.AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER
import android.media.AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE
import renetik.android.core.kotlin.primitives.asInt

val AudioManager.outputFramesPerBuffer: Int
    get() = getProperty(PROPERTY_OUTPUT_FRAMES_PER_BUFFER)
        ?.asInt().takeUnless { it == 0 } ?: 256

val AudioManager.outputSampleRate: Int
    get() = getProperty(PROPERTY_OUTPUT_SAMPLE_RATE)
        ?.asInt().takeUnless { it == 0 } ?: 44100