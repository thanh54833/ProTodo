package com.example.protodo.example.exercise.MVI.api

import com.example.protodo.example.exercise.MVI.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelperImpl @Inject constructor() : ApiHelper {
    //private val apiService: ApiService
    private var apiService = RetrofitBuilder.apiService
    override suspend fun getUsers(): List<User> {


        return apiService.getUsers()
    }
}