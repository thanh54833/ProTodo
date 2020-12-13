package com.example.protodo.example.exercise.dagger2.di

import com.example.protodo.example.exercise.dagger2.Dagger2Act
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
    abstract fun contributeMainActivity(): Dagger2Act
}
