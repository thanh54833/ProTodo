package com.example.protodo.example.exercise.MVI.api

import com.example.protodo.example.exercise.MVI.model.User

interface ApiHelper {
    suspend fun getUsers(): List<User>
}