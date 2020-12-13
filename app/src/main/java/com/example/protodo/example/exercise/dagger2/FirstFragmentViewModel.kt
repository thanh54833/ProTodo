package com.example.protodo.example.exercise.dagger2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import androidx.lifecycle.liveData
import javax.inject.Inject

class FirstFragmentViewModel @Inject constructor() : ViewModel() {
    //    private val _infoText = liveData {
//        while (true) {
//            emit(System.currentTimeMillis().toString())
//            delay(1000)
//        }
//    }
    val infoText = MutableLiveData(1000) //: LiveData<String> = //_infoText
}
