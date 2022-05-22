package com.smt.chat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smt.chat.cost.KConst
import com.smt.chat.databinding.ActivityMainBinding
import com.smt.chat.model.MainViewModel
import com.smt.chat.model.Message
import org.json.JSONObject
import sock.SocketClient
import java.net.Socket

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    private val message = arrayListOf<Message>()

    private val adapter: ExtensionDataAdapter by lazy {
        ExtensionDataAdapter()
    }

    private val viewmodel by viewModels<MainViewModel>()

    private val client: SocketClient by lazy {
        SocketClient(6789)
    }

    var respanse: Thread? =
        Thread{
            client?.let {
//                var isRead = false
                while (true) {
                    it.read() { res ->
                        val respObj =   JSONObject(res)
                        runOnUiThread {
                            adapter.setData(Message("res: ${respObj.getString(KConst.MESSAGE_KEY)}", 2))
                        }

                    }
                }
//                if (isRead) {
//                    it.closeConnect()
//                }
            }


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter =   adapter
        binding.button.setOnClickListener {
            println("[${binding.sendText.text.toString()}]   is value ")
            sendMeg(binding.sendText.text.toString())
            adapter.setData(Message(binding.sendText.text.toString()))
            binding.sendText.text.clear()
            binding.recyclerView.scrollToPosition(adapter.data.size - 1)
            /*
            viewmodel.addItem(Message(binding.sendText.text.toString(), "보냅니다"))
            viewmodel.itemList.observe(this,{ msg ->
                adapter.data = msg
                binding.sendText.text.clear()
//                binding.recyclerView.adapter?.notifyDataSetChanged()
            })

            viewmodel.itemList.value?.let{
                binding.recyclerView.scrollToPosition(it.size - 1)
                adapter?.notifyDataSetChanged()
            }
            */
        }

    }

    fun sendMeg(msg: String){

//        respanse.let {
//            if(it?.isAlive == false) {
//                it?.start()
//            }
//        }

        var jsondata = JSONObject().apply {
            put(KConst.MESSAGE_KEY,msg)
            put(KConst.MESSAGE_ID,"jung")
        }
        val jsonString = jsondata.toString()
        Thread {
            client?.let {
                it.sendData(jsonString)
                it.flush()

                while (true) {

                    it.read() { res ->
                        res?.let{
                            val respObj =   JSONObject(res)
                            runOnUiThread {
                                adapter.setData(Message("res: ${respObj.getString(KConst.MESSAGE_KEY)}", 2))

                            }
                        }

                    }
                }
//                println(" Thread run")
//                runOnUiThread {
//                    adapter.setData(Message("res: ${appendString.toString()}", 2))
//                }
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        respanse?.let {
            if(it.isAlive) {
                println(" isAlive ===========")

                it.isInterrupted
            }
        }
        client?.closeConnect()
        respanse = null
        println(" isAlive =========== is null")
    }

}