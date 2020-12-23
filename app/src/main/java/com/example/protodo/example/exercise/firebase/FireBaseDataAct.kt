package com.example.protodo.example.exercise.firebase

import android.os.Parcelable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.FireBaseActBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.parcel.Parcelize

/**
 * Doc : https://firebase.google.com/docs/database/android/start#kotlin+ktx_1
 *
 */

class FireBaseDataAct(override val viewModel: ViewModel) : ProTodActivity() {
    lateinit var binding: FireBaseActBinding

    override fun initiativeView() {
        "FireBaseAct :...".Log()

        binding = DataBindingUtil.setContentView(this@FireBaseDataAct, R.layout.fire_base_act)
    }

    /**
     * https://console.firebase.google.com/project/protodo-ad10d/database/protodo-ad10d-default-rtdb/data
     * Thanh test firebase data ...
     *
     */
    override fun setupUI() {
        // Write a message to the database
        val database = Firebase.database.reference
        database.child("age").setValue(50)
        database.child("age").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                " snapshot.value :... ${snapshot.value} ".Log()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        database.child("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { _snapshot ->
                    "_snapshot :.. ${_snapshot.value} ".Log()
                }
                " snapshot.value :... ${snapshot.value} ".Log()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        val data = listOf(List("key1", "value1", 1), List("key2", "value2", 2))
        //database.child("data") //setValue(data)
        database.child("data").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                "snapshot :... $snapshot ".Log()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    @Parcelize
    class List(
        var key: String,
        var value: String,
        var act: Int
    ) : Parcelable

    override fun observeViewModel() {

    }
}