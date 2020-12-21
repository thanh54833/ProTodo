package com.example.protodo.example.exercise.MVI

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.ItemViewBinding
import com.example.protodo.databinding.MviActBinding
import com.example.protodo.example.exercise.MVI.model.User
import com.example.protodo.example.exercise.MVI.view.MainAdapter
import com.example.protodo.example.exercise.MVI.view.MainIntent
import com.halo.widget.recycle.HolderBase
import com.halo.widget.recycle.setContentView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class MVIAct : ProTodActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @ExperimentalCoroutinesApi
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }
    private var adapter = MainAdapter(arrayListOf())
    private lateinit var binding: MviActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@MVIAct, R.layout.mvi_act)

        "MVIAct :..".Log()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        
//        binding.buttonFetchUser.setOnClickListener {
//            lifecycleScope.launch {
//                mainViewModel.userIntent.send(MainIntent.FetchUser)
//            }
//        }

        //call api ....
        lifecycleScope.launch {
            mainViewModel.userIntent.send(MainIntent.FetchUser)
        }
    }

    override fun setupUI() {

    }

    private fun setupViewModel() {
//        mainViewModel = ViewModelProviders.of(
//            this,
//            ViewModelFactory(
//                ApiHelperImpl(
//                    RetrofitBuilder.apiService
//                )
//            )
//        ).get(MainViewModel::class.java)
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                //"mainViewModel.state :... $it ".Log()
                when (it) {
                    is MainState.Idle -> {
                        "MainState.Idle :... ".Log()
                        //Todo :  thanh create data for view :...
                        //val user = User()
                        //renderList(listOf(user, user, user, user, user))
//                        binding.progressBar.visibility = View.GONE
//                        binding.buttonFetchUser.visibility = View.GONE
//                        binding.recyclerView.visibility = View.VISIBLE
                    }
                    is MainState.Loading -> {
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        "MainState.Users :..".Log()
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MVIAct, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderList(users: List<User>) {

        "users :... ${users.size} ".Log()

//        binding.recyclerView.visibility = View.VISIBLE
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.run {
//            addItemDecoration(
//                DividerItemDecoration(
//                    binding.recyclerView.context,
//                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
//                )
//            )
//        }
//        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
//        binding.recyclerView.adapter = adapter
        //adapter.notifyDataSetChanged()

        binding.recyclerView.setContentView(
            users,
            HolderBase<ItemViewBinding, Interface>(this@MVIAct, R.layout.item_view)
        ) { _binding, _item, _ ->
            _binding.textView.text = _item?.data?.name + "---" + _item?.position
        }


    }

    interface Interface {

    }
}
