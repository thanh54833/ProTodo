package com.example.protodo.example.exercise.MVI


sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Users(val user: List<User>) : MainState()
    data class Error(val error: String?) : MainState()
}