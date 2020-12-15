package com.example.protodo.example.exercise.MVI

import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : MainRepository {
    override suspend fun getUsers() = apiHelper.getUsers()
}

interface MainRepository {
    suspend fun getUsers(): List<User>
}