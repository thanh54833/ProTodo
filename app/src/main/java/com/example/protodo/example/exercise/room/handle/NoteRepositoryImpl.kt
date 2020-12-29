package com.example.protodo.example.exercise.room.handle

import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(noteDatabase: NoteDatabase) :
    NoteRepository(noteDatabase) {


}