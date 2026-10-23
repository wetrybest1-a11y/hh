package com.example.game

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.math.PI
import kotlin.math.sin

object SoundSynthesizer {
    private const val SAMPLE_RATE = 22050
    private val random = Random()
    private val scope = CoroutineScope(Dispatchers.Default)

    private fun playPcm(buffer: ShortArray) {
        scope.launch {
            try {
                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(SAMPLE_RATE)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, buffer.size)
                track.play()
                // Auto release after sound finishes
                Thread.sleep((buffer.size * 1000L) / SAMPLE_RATE + 50)
                track.release()
            } catch (e: Exception) {
                // Silently handle if audio subsystem is busy
            }
        }
    }

    fun playGunshot() {
        val durationMs = 160
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val progress = i.toFloat() / numSamples
            val envelope = (1.0f - progress) * (1.0f - progress)
            // White noise mixed with low-frequency punch
            val noise = (random.nextFloat() * 2f - 1f) * 0.7f
            val punch = sin(2.0 * PI * 120.0 * (1.0 - progress) * (i.toDouble() / SAMPLE_RATE)).toFloat() * 0.5f
            val sample = ((noise + punch) * envelope * 30000).toInt().coerceIn(-32768, 32767)
            buffer[i] = sample.toShort()
        }
        playPcm(buffer)
    }

    fun playHitMarker() {
        val durationMs = 70
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)
        val freq = 1200.0 // High-pitched ping

        for (i in 0 until numSamples) {
            val progress = i.toFloat() / numSamples
            val envelope = 1.0f - progress
            val wave = sin(2.0 * PI * freq * (i.toDouble() / SAMPLE_RATE)).toFloat()
            val sample = (wave * envelope * 22000).toInt().coerceIn(-32768, 32767)
            buffer[i] = sample.toShort()
        }
        playPcm(buffer)
    }

    fun playReload() {
        val durationMs = 280
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)

        // Two distinct mechanical clicks
        for (i in 0 until numSamples) {
            val t = i.toFloat() / SAMPLE_RATE
            var click = 0f
            if (t in 0.04f..0.08f) {
                val p = (t - 0.04f) / 0.04f
                click += (random.nextFloat() * 2f - 1f) * (1f - p) * 0.8f
            }
            if (t in 0.16f..0.22f) {
                val p = (t - 0.16f) / 0.06f
                click += (random.nextFloat() * 2f - 1f) * (1f - p) * 1.0f
            }
            val sample = (click * 24000).toInt().coerceIn(-32768, 32767)
            buffer[i] = sample.toShort()
        }
        playPcm(buffer)
    }

    fun playZoneWarning() {
        val durationMs = 350
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val t = i.toDouble() / SAMPLE_RATE
            // Siren modulation
            val freq = 440.0 + sin(2.0 * PI * 8.0 * t) * 80.0
            val wave = sin(2.0 * PI * freq * t).toFloat()
            val sample = (wave * 18000).toInt().coerceIn(-32768, 32767)
            buffer[i] = sample.toShort()
        }
        playPcm(buffer)
    }

    fun playLootPickup() {
        val durationMs = 120
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)
        val freq1 = 880.0
        val freq2 = 1320.0

        for (i in 0 until numSamples) {
            val progress = i.toFloat() / numSamples
            val envelope = 1.0f - progress
            val currentFreq = if (progress < 0.5f) freq1 else freq2
            val wave = sin(2.0 * PI * currentFreq * (i.toDouble() / SAMPLE_RATE)).toFloat()
            val sample = (wave * envelope * 24000).toInt().coerceIn(-32768, 32767)
            buffer[i] = sample.toShort()
        }
        playPcm(buffer)
    }

    fun playVictoryFanfare() {
        scope.launch {
            val notes = listOf(523.25, 659.25, 783.99, 1046.50) // C5, E5, G5, C6
            for (freq in notes) {
                val durationMs = 140
                val numSamples = (SAMPLE_RATE * durationMs) / 1000
                val buffer = ShortArray(numSamples)
                for (i in 0 until numSamples) {
                    val progress = i.toFloat() / numSamples
                    val envelope = 1.0f - progress * 0.4f
                    val wave = sin(2.0 * PI * freq * (i.toDouble() / SAMPLE_RATE)).toFloat()
                    buffer[i] = (wave * envelope * 25000).toInt().coerceIn(-32768, 32767).toShort()
                }
                playPcm(buffer)
                Thread.sleep(120)
            }
        }
    }

    fun playButtonClick() {
        val durationMs = 30
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val progress = i.toFloat() / numSamples
            val wave = sin(2.0 * PI * 800.0 * (i.toDouble() / SAMPLE_RATE)).toFloat()
            buffer[i] = (wave * (1.0f - progress) * 16000).toInt().toShort()
        }
        playPcm(buffer)
    }
}
