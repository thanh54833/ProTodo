package com.example.protodo.example.exercise.room

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.protodo.R
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.RoomActBinding
import com.example.protodo.example.exercise.MVI.MainViewModel
import com.example.protodo.example.exercise.room.handle.Note
import com.example.protodo.example.exercise.room.handle.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


/**
 *
 *  Thanh đang lam  example room database trong MVVM
 *  https://rivaldy.medium.com/room-database-with-mvvm-architecture-android-jetpack-crud-25dd0f476514
 *
 */

class RoomAct : ProTodActivity() {

    @ExperimentalCoroutinesApi
    override val viewModel: RoomViewModel by viewModels { viewModelFactory }
    lateinit var binding: RoomActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@RoomAct, R.layout.room_act)

    }

    override fun setupUI() {

    }

    override fun observeViewModel() {

        insertData()

    }

    @ExperimentalCoroutinesApi
    private fun insertData() {
        val title = "this is sample title"
        val desc = "this is sample desc"
        val priority = 1 //sample

        val note = Note(
            id = null,
            title = title,
            description = desc,
            priority = priority
        ) //why id null? because id is auto generate
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.insertNote(note).also {
                //do action here

            }
        }
    }

}


class RoomViewModel @javax.inject.Inject constructor(private val repository: NoteRepository) :
    ViewModel() {

    suspend fun insertNote(note: Note) = repository.insertNote(note)

    suspend fun updateNote(note: Note) = repository.updateNote(note)

    suspend fun deleteNote(note: Note) = repository.deleteNote(note)

    suspend fun deleteNoteById(id: Int) = repository.deleteNoteById(id)

    suspend fun clearNote() = repository.clearNote()

    fun getAllNotes() = repository.getAllNotes()

}


