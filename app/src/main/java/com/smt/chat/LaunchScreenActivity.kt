package com.smt.chat

import android.content.Intent
import android.os.*
import android.widget.Toast
import com.smt.chat.cost.KConst
import com.smt.chat.databinding.ActivityLaunchScreenBinding
import com.smt.chat.service.*
import com.smt.chat.uiif.UIInterface
import org.json.JSONObject

class LaunchScreenActivity : BaseActivity() {

    private lateinit var binding: ActivityLaunchScreenBinding

    override fun onDestroy() {
        super.onDestroy()
//        unbindBinderExtendedService()
        println(" LaunchScreenActivity onDestroy =========== is null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaunchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ChatAppliction.setCallback(object: UIInterface{
            override fun execute(json: JSONObject) {
                json?.let {
                    if (it.get(KConst.NICKNAME_CHK) != "Y") {
                        runOnUiThread {
                            Toast.makeText(this@LaunchScreenActivity, "${json}", Toast.LENGTH_LONG).show()
                        }
                        return@let
                    }

                    Intent(this@LaunchScreenActivity, MainActivity::class.java).apply {
                        this.putExtra(KConst.NICKNAME_KEY,it.get(KConst.NICKNAME_KEY).toString())
                        this.putExtra(KConst.NICKNAME_CHK,it.get(KConst.NICKNAME_CHK).toString())
                        startActivity(this)
                        finish()
                    }
                }
            }

        })

        binding.btnSend.setOnClickListener {
            val nikname = binding.etNikname.text.toString()

            nikname?.let{

                var jsondata = JSONObject().apply {
                    put(KConst.MESSAGE_DATA, "connection request")
                    put(KConst.NICKNAME_KEY, nikname)
                    put(KConst.NICKNAME_CHK, "N")
                }
                ChatAppliction.instance.registerService?.send(jsondata.toString())

            }
        }

        println(" LaunchScreenActivity count === ${ChatAppliction.count}")

        ChatAppliction.count++
//        bindBinderExtendedService()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        disconnetRequest()
    }


//    private fun bindBinderExtendedService() {
//        Intent(this, RegisterService::class.java).run {
//            bindService(this, ChatAppliction.getInstance().connection, Service.BIND_AUTO_CREATE)
//        }
//    }

//    private fun unbindBinderExtendedService() {
//        unbindService(ChatAppliction.getInstance().connection)
//    }

}
