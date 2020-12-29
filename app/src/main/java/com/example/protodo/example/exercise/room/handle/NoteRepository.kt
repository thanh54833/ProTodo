package com.example.protodo.example.exercise.room.handle

import androidx.lifecycle.LiveData

open class NoteRepository(
    private val noteDatabase: NoteDatabase
) {
    suspend fun insertNote(note: Note) = noteDatabase.getNoteDao().insertNote(note)

    suspend fun updateNote(note: Note) = noteDatabase.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = noteDatabase.getNoteDao().deleteNote(note)

    suspend fun deleteNoteById(id: Int) = noteDatabase.getNoteDao().deleteNoteById(id)

    suspend fun clearNote() = noteDatabase.getNoteDao().clearNote()

    fun getAllNotes(): LiveData<List<Note>> = noteDatabase.getNoteDao().getAllNotes()
}