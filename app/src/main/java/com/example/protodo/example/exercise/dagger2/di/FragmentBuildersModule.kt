package com.example.protodo.example.exercise.dagger2.di

import com.example.protodo.example.exercise.dagger2.FirstFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused") // Suppress : bỏ di  mấy cái cảnh báo ...
@Module // Module :
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFirstFragment(): FirstFragment
}
