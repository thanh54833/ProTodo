package com.example.protodo.example.exercise.MVI.di

import com.example.protodo.example.exercise.MVI.api.ApiHelper
import com.example.protodo.example.exercise.MVI.model.User
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : MainRepository {
    override suspend fun getUsers() = apiHelper.getUsers()
}

interface MainRepository {
    suspend fun getUsers(): List<User>
}