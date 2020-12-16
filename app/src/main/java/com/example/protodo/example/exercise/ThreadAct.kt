package com.example.protodo.example.exercise

import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Runnable


class ThreadAct : ProTodActivity() {

    override fun initiativeView() {
//        Example2.main()
//        MainScope().launch {
//            delay(2000)
//            Example2.onClick()
//        }
//
//        rootView?.setOnClickListener {
//            Example2.onClick()
//        }

        //Example3.main()
        //Example1.main()
        //Example4.main()

        Example6.main()


    }

    override fun setupUI() {
        //TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        //TODO("Not yet implemented")
    }
}

//Todo : Làm 1 cái ví dụ về đồng bộ và bất đồng bộ :...
object Example1 {
    fun main() {
        val c = Count()

        // Tạo 2 thread truy cập vào cùng tài nguyên trong 1 đối tượng
        val t1 = Thread(Access("Thread 1", c))
        val t2 = Thread(Access("Thread 2", c))
        t1.start()
        t2.start()

        "ThreadAct ".Log()
    }

    class Count {
        var value: Int = 10
    }

    class Access(private var name: String, private var count: Count? = null) : Runnable {
        override fun run() {
            @Synchronized
            fun action() {
                for (index in 0 until 3) {
                    (name + " " + "index " + index + " before: " + count?.value).Log()
                    count?.apply {
                        value--
                    }
                    (name + " " + "index " + index + " after: " + count?.value).Log()
                }
            }
            action()
        }
    }
}

object Example2 {
    private lateinit var flow: Flow<Int>
    fun main() {
        setupFlow()
    }

    fun onClick() {
        CoroutineScope(Dispatchers.Main).launch {
            flow.collect { _item ->
                "collect :... ${_item} ".Log()

            }
        }
    }

    private fun setupFlow() {
        flow = flow {
            ("Start flow").Log()
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
                //("Emitting $it").Log()
                emit(it)
            }
        }.flowOn(Dispatchers.Default)
    }
}

object Example3 {
    fun main() {
        val model = Model()
        "start :...".Log()
        access(model)
        accessV2(model)
        "end :...".Log()
    }

    private fun access(model: Model) {
        model.value = 10
    }

    private fun accessV2(model: Model) {
        model.value = 12
        "model value :... ${model.value} ".Log()
    }

    class Model(var value: Int = 0)
}

object Example4 {
    fun main() {
        "Example4 :...".Log()
        val flow = flowOf("1", "2", "3").onEach {
            delay(100)
        }.flowOn(Dispatchers.Default)
        CoroutineScope(Dispatchers.Default).launch {
            flow.collect { _it ->
                "collect :... $_it ".Log()
            }
        }
        "end Example4 :...".Log()
    }
}

object Example5 {
    @ExperimentalCoroutinesApi
    fun main() {
        val flow = channelFlow {
            (0..10).forEach {
                send(it)
            }
        }.flowOn(Dispatchers.Default)
        CoroutineScope(Dispatchers.Default).launch {
            flow.collect {

            }
        }
    }
}

object Example6 {
    fun main() {
        "Example6 :..".Log()
        val flow1 = flowOf("1", "2", "3").onEach {
            delay(100)
        }.flowOn(Dispatchers.Default)
        val flow2 =
            flowOf("1_1", "2_2", "3_3", "4_4").onEach { delay(500) }.flowOn(Dispatchers.Default)
        CoroutineScope(Dispatchers.Main).launch {
            flow1.zip(flow2) { _firstString, _lastString ->
                "_firstString :.. ${_firstString} __ _lastString :...${_lastString} "
            }.collect {
                "flowOf :.. $it ".Log()
            }
        }
    }
}
