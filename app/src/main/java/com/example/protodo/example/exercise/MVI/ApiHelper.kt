package com.example.protodo.example.exercise.MVI

interface ApiHelper {
    suspend fun getUsers(): List<User>
}