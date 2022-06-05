package com.smt.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smt.chat.cost.KConst
import com.smt.chat.databinding.ActivityMainBinding
import com.smt.chat.model.MainViewModel
import com.smt.chat.model.Message
import com.smt.chat.service.ChatAppliction
import com.smt.chat.uiif.UIInterface
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    private val adapter: ExtensionDataAdapter by lazy {
        ExtensionDataAdapter()
    }

    private val viewmodel by viewModels<MainViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        val nickNameKey = intent.getStringExtra(KConst.NICKNAME_KEY) ?: ""
        val nickNameChk = intent.getStringExtra(KConst.NICKNAME_CHK) ?: ""

        var jsondata = JSONObject().apply {
            put(KConst.NICKNAME_KEY, nickNameKey)
            put(KConst.NICKNAME_CHK, nickNameChk)
        }

        ChatAppliction.userInfo = jsondata
        binding.recyclerView.adapter = adapter
        binding.button.setOnClickListener {

            //본인글 화면에 출력
            adapter.setData(
                Message(
                    nickNameKey,
                    binding.sendText.text.toString(),
                    1
                )
            )

            jsondata.apply {
                put(KConst.MESSAGE_DATA, binding.sendText.text.toString())
            }
            //서버에 메세지 전송
            ChatAppliction.registerService?.send(jsondata.toString(),false)
            binding.sendText.text.clear()

        }

        ChatAppliction.setCallback(object: UIInterface {
            override fun execute(json: JSONObject) {
                json?.let {
                    runOnUiThread {

                        val resData = json.get(KConst.MESSAGE_DATA).toString()
                        if(resData == "CLOSED"){
                            ChatAppliction.instance.disconnect(this@MainActivity)
                        }
                        Toast.makeText(this@MainActivity, "${json.toString()}", Toast.LENGTH_LONG).show()
                    }

                }
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        disconnet()
    }


}