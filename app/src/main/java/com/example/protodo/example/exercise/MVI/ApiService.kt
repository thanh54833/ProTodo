package com.example.protodo.example.exercise.MVI

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}