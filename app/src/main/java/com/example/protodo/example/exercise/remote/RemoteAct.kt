package com.example.protodo.example.exercise.remote

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.RemoteActBinding
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetAddress
import java.net.Socket


class RemoteAct(override val viewModel: ViewModel) : ProTodActivity(), View.OnClickListener {
    lateinit var binding: RemoteActBinding
    var context: Context? = null
    var playPauseButton: Button? = null
    var nextButton: Button? = null
    var previousButton: Button? = null
    var mousePad: TextView? = null

    private var isConnected = false
    private var mouseMoved = false
    private var socket: Socket? = null
    private var out: PrintWriter? = null

    private var initX = 0f
    private var initY = 0f
    private var disX = 0f
    private var disY = 0f


    override fun initiativeView() {
        //binding = DataBindingUtil.setContentView(this@RemoteAct, R.layout.remote_act)
        setContentView(R.layout.remote_act)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {

        // on connect pc ..
        val connectPhoneTask = ConnectPhoneTask()
        connectPhoneTask.execute(Constants.SERVER_IP) //try to connect to server in another thread

        context = this //save the context to show Toast messages

        //Get references of all buttons

        //Get references of all buttons
        playPauseButton = findViewById<View>(R.id.playPauseButton) as Button
        nextButton = findViewById<View>(R.id.nextButton) as Button
        previousButton = findViewById<View>(R.id.previousButton) as Button

        //this activity extends View.OnClickListener, set this as onClickListener
        //for all buttons

        //this activity extends View.OnClickListener, set this as onClickListener
        //for all buttons
        playPauseButton!!.setOnClickListener(this)
        nextButton!!.setOnClickListener(this)
        previousButton!!.setOnClickListener(this)

        //Get reference to the TextView acting as mousepad

        //Get reference to the TextView acting as mousepad
        mousePad = findViewById<View>(R.id.mousePad) as TextView

        //capture finger taps and movement on the textview

        //capture finger taps and movement on the textview
        mousePad?.setOnTouchListener { v, event ->
            if (isConnected && out != null) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        //save X and Y positions when user touches the TextView
                        initX = event.x
                        initY = event.y
                        mouseMoved = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        disX = event.x - initX //Mouse movement in x direction
                        disY = event.y - initY //Mouse movement in y direction
                        /*set init to new position so that continuous mouse movement
                        is captured*/initX = event.x
                        initY = event.y
                        if (disX != 0f || disY != 0f) {
                            out!!.println("$disX,$disY") //send mouse movement to server
                        }
                        mouseMoved = true
                    }
                    MotionEvent.ACTION_UP ->                             //consider a tap only if usr did not move mouse after ACTION_DOWN
                        if (!mouseMoved) {
                            out!!.println(Constants.MOUSE_LEFT_CLICK)
                        }
                }
            }
            true
        }
    }

    override fun observeViewModel() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.itemId
        if (id == R.id.action_connect) {
            val connectPhoneTask = ConnectPhoneTask()
            connectPhoneTask.execute(Constants.SERVER_IP) //try to connect to server in another thread
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //OnClick method is called when any of the buttons are pressed
    override fun onClick(v: View) {
        when (v.id) {
            R.id.playPauseButton -> if (isConnected && out != null) {
                out?.println(Constants.PLAY) //send "play" to server
            }
            R.id.nextButton -> if (isConnected && out != null) {
                out?.println(Constants.NEXT) //send "next" to server
            }
            R.id.previousButton -> if (isConnected && out != null) {
                out?.println(Constants.PREVIOUS) //send "previous" to server
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isConnected && out != null) {
            try {
                out?.println("exit") //tell server to exit
                socket!!.close() //close socket
            } catch (e: IOException) {
                ("Error in closing socket").Log("remotedroid")
            }
        }
    }

    inner class ConnectPhoneTask : AsyncTask<String?, Void?, Boolean>() {
        override fun onPostExecute(result: Boolean) {
            "onPostExecute :...".Log()

            isConnected = result

            Toast.makeText(
                context,
                if (isConnected) "Connected to server!" else "Error while connecting",
                Toast.LENGTH_LONG
            ).show()

            try {
                if (isConnected) {
                    out = PrintWriter(
                        BufferedWriter(
                            OutputStreamWriter(
                                socket?.getOutputStream()
                            )
                        ), true
                    ) //create output stream to send data to server
                }
            } catch (e: IOException) {
                ("Error while creating OutWriter" + e.message).Log("remotedroid")
                Toast.makeText(context, "Error while connecting", Toast.LENGTH_LONG).show()
            }
        }

        override fun doInBackground(vararg params: String?): Boolean {
            "doInBackground :...".Log()
            var result = true
            try {
                val serverAddr: InetAddress = InetAddress.getByName(params[0])
                //Open socket on server IP and port
                socket = Socket(serverAddr, Constants.SERVER_PORT)

            } catch (e: IOException) {
                "Error while connecting :... ${e.message} ".Log()
                result = false
            }
            return result
        }
    }
}