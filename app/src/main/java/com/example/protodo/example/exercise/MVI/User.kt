package com.example.protodo.example.exercise.MVI

import com.squareup.moshi.Json

data class User(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "name",
    @Json(name = "email")
    val email: String = "email@gmail.com",
    @Json(name = "avatar")
    val avatar: String = "avatar"
)