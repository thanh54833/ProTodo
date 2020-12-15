package com.example.protodo.example.exercise.dagger2.di

import com.example.protodo.example.exercise.dagger2.module.TestModule
import dagger.Module

@Module(includes = [
    ViewModelModule::class,
    TestModule::class
])
class AppModule {

}
