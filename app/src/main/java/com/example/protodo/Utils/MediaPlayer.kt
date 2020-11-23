package com.example.protodo.Utils

import android.content.Context
import android.media.MediaPlayer
import com.example.protodo.R

//Todo :
class MediaPlayer(var context: Context, var mode: Mode = Mode.BUTTON) {
    private var mp: MediaPlayer? = null

    enum class Mode {
        BUTTON
    }

    fun start() {
        when (mode) {
            Mode.BUTTON -> {
                mp = MediaPlayer.create(context, R.raw.button_click_sound_v2)
            }
            else -> {
                mp = MediaPlayer.create(context, R.raw.button_click_sound)
            }
        }
        if (mp?.isPlaying == true) {
            mp?.stop()
            mp?.release()
        }
        mp?.start()
    }
}