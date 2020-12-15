package com.example.protodo.example.exercise.dagger2.module

import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class TestModule @Inject constructor() {
    @Provides
    @Singleton
    fun getSumUseCase(): SumUseCase {
        return SumUseCase().apply {
            name = "name"
            age = 123
        }
    }
}

class SumUseCase(var name: String? = "", var age: Int? = 10)