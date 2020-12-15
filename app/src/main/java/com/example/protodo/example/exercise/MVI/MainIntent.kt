package com.example.protodo.example.exercise.MVI

sealed class MainIntent {
    object FetchUser : MainIntent()
}