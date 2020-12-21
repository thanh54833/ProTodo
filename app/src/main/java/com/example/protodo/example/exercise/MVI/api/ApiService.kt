package com.example.protodo.example.exercise.MVI.api

import com.example.protodo.example.exercise.MVI.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}