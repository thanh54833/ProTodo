package com.example.protodo.example.exercise.speech

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.SpeechActBinding
import com.example.protodo.example.exercise.MVI.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class SpeechAct : ProTodActivity(), TextToSpeech.OnInitListener {
    private var myTTS: TextToSpeech? = null

    @ExperimentalCoroutinesApi
    override val viewModel: MainViewModel by viewModels { viewModelFactory }

    //status check code
    private val MY_DATA_CHECK_CODE = 0
    lateinit var binding: SpeechActBinding

    //val ss: SpeechActBinding by lazy { getBinding<SpeechActBinding>() }

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@SpeechAct, R.layout.speech_act)

    }

    override fun setupUI() {

        //val speakButton: Button = findViewById<View>(R.id.speak) as Button
        //listen for clicks
        //listen for clicks
        //speakButton.setOnClickListener(this)

        //check for TTS data

        //check for TTS data
        val checkTTSIntent = Intent()
        checkTTSIntent.action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE)

        binding.button.setOnClickListener {
            "binding.button.setOnClickListener  :...".Log()
            val words = "work later than"//enteredText.getText().toString()
            speakWords(words)
        }

    }

    override fun observeViewModel() {

    }

//    fun <T> lazyEx(initializer: () -> T): LazyEx<T> = LazyEx(initializer)
//    class LazyEx<out T>(private var initializer: () -> T) : Lazy<T> {
//
//        @Volatile
//        private var wrap = Wrap()
//        override val value: T get() = wrap.lazy.value
//        override fun isInitialized() = wrap.lazy.isInitialized()
//        override fun toString() = wrap.lazy.toString()
//        fun invalidate() {
//            wrap = Wrap()
//        } // create a new Wrap object
//
//        private inner class Wrap {
//            val lazy = lazy(initializer)
//        }
//    }


    //speak the user text
    private fun speakWords(speech: String) {

        //speak straight away
        myTTS?.speak(speech, TextToSpeech.QUEUE_FLUSH, null)
    }

    //act on result of TTS data check
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = TextToSpeech(this, this)
            } else {
                //no data - install it now
                val installTTSIntent = Intent()
                installTTSIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
                startActivity(installTTSIntent)
            }
        }
    }

    //setup TTS
    override fun onInit(initStatus: Int) {
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            " TextToSpeech.SUCCESS:... ".Log()
            //if (myTTS?.isLanguageAvailable(Locale.US) === TextToSpeech.LANG_AVAILABLE) myTTS?.language = Locale.US
            myTTS?.language = Locale.US


            val a: MutableSet<String> = HashSet()
            a.add("male") //here you can give male if you want to select male voice.

            //Voice v=new Voice("en-us-x-sfg#female_2-local",new Locale("en","US"),400,200,true,a);
            //val v = Voice("en-us-x-sfg#female_2-local", Locale("en", "US"), 400, 200, true, a);
            val v = Voice("en-us-x-sfg#male_2-local", Locale("en", "US"), 400, 200, true, a)

            //myTTS?.voice = v
            //myTTS?.setSpeechRate(0.8f)


            // int result = T2S.setLanguage(Locale.US);
            // int result = T2S.setLanguage(Locale.US);
            //val result: Int = myTTS?.setVoice(v) ?: 0


            val voiceobj = Voice(
                "it-it-x-kda#male_2-local",
                Locale.getDefault(), 1, 1, false, null
            )

            val result = myTTS?.setVoice(voiceobj)
            val text = "work later than"
            result.Log("result:...")

            myTTS?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)


//            if (result == TextToSpeech.LANG_MISSING_DATA
//                || result == TextToSpeech.LANG_NOT_SUPPORTED
//            ) {
//                ("This Language is not supported").Log()
//            } else {
//                // btnSpeak.setEnabled(true);
//
//                "speakOut(myTTS?.) :...".Log()
//                speakOut("myTTS")
//            }


        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show()
        }
    }

    private fun speakOut(message: String) {
        myTTS?.speak(message, TextToSpeech.QUEUE_FLUSH, null)
    }

}