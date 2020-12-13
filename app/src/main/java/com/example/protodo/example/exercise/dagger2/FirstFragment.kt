package com.example.protodo.example.exercise.dagger2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.protodo.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FirstFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FirstFragmentViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var timestampTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_number, container, false).also {
            //timestampTextView = it.findViewById(R.id.timestamp)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.infoText.observe(viewLifecycleOwner, Observer {
            timestampTextView.text = "current time = $it"
        })
    }
}