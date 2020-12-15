package com.example.protodo.example.exercise.dagger2.di

import com.example.protodo.example.exercise.MVI.MVIAct
import com.example.protodo.example.exercise.dagger2.Dagger2Act
import com.example.protodo.example.exercise.dagger2.Dagger3Act
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.DaggerActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )
    abstract fun bindDagger3Act(): Dagger3Act

    @ContributesAndroidInjector
    abstract fun bindDagger2Act(): Dagger2Act

    @ContributesAndroidInjector
    abstract fun bindMviAct(): MVIAct
}
