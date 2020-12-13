package com.example.protodo.example.exercise.dagger2.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.DaggerActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun contributeMainActivity(): DaggerActivity
}
