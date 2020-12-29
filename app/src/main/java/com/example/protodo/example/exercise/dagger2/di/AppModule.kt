package com.example.protodo.example.exercise.dagger2.di

import android.app.Application
import com.example.protodo.example.exercise.dagger2.module.TestModule
import com.example.protodo.example.exercise.room.handle.NoteDao
import com.example.protodo.example.exercise.room.handle.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class,
        TestModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): NoteDatabase {
        return NoteDatabase.invoke(app)
    }

    @Singleton
    @Provides
    fun provideLoginDao(db: NoteDatabase): NoteDao {
        return db.getNoteDao()
    }

}
