package com.example.protodo.example.exercise.dagger2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.protodo.example.exercise.MVI.*
import com.example.protodo.example.exercise.MVI.api.ApiHelper
import com.example.protodo.example.exercise.MVI.api.ApiHelperImpl
import com.example.protodo.example.exercise.MVI.di.MainRepository
import com.example.protodo.example.exercise.MVI.di.MainRepositoryImpl
import com.example.protodo.example.exercise.dagger2.FirstFragmentViewModel
import com.example.protodo.example.exercise.dagger2.ViewModelFactory
import com.example.protodo.example.exercise.room.RoomViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindFirstFragmentViewModel(fragmentViewModel: FirstFragmentViewModel): ViewModel

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Singleton
    @Binds
    abstract fun bindRoomViewModel(roomViewModel: RoomViewModel): ViewModel


    @Singleton
    @Binds
    abstract fun bindsCountryRepository(repository: ApiHelperImpl): ApiHelper

    @Singleton
    @Binds
    abstract fun bindsMainRepositoryImpl(repository: MainRepositoryImpl): MainRepository


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
