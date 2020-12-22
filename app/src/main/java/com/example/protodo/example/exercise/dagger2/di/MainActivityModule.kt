package com.example.protodo.example.exercise.dagger2.di

import com.example.protodo.example.exercise.MVI.MVIAct
import com.example.protodo.example.exercise.api.ApiAct
import com.example.protodo.example.exercise.dagger2.Dagger2Act
import com.example.protodo.example.exercise.dagger2.Dagger3Act
import com.example.protodo.example.exercise.firebase.FireBaseDataAct
import com.example.protodo.example.exercise.firebase.FirebaseMessage
import com.example.protodo.example.exercise.remote.RemoteAct
import dagger.Module
import dagger.android.ContributesAndroidInjector

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

    @ContributesAndroidInjector
    abstract fun bindApiAct(): ApiAct

    @ContributesAndroidInjector
    abstract fun bindRemoteAct(): RemoteAct

    @ContributesAndroidInjector
    abstract fun bindFireBaseAct(): FireBaseDataAct

    @ContributesAndroidInjector
    abstract fun bindFirebaseMessage(): FirebaseMessage
    
}
