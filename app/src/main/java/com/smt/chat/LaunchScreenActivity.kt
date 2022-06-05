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
        println(" LaunchScreenActivity onDestroy =========== is null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaunchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //socket callback 응답처리
        ChatAppliction.setCallback(object: UIInterface{
            override fun execute(json: JSONObject) {

                println(" LaunchScreenActivity setCallback  json ${json.toString()}")
                json?.let {
                    if (it.get(KConst.NICKNAME_CHK) != "Y") {
                        //닉네임 중복체크 결과
                        runOnUiThread {
                            Toast.makeText(this@LaunchScreenActivity, "${json}", Toast.LENGTH_LONG).show()
                        }
                        return@let
                    }

                    //Chating 화면전환
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

                //Chating 닉네임 및 사용자 정보등록
                var jsondata = JSONObject().apply {
                    put(KConst.MESSAGE_DATA, "connection request")
                    put(KConst.NICKNAME_KEY, nikname)
                    put(KConst.NICKNAME_CHK, "N")
                }
                ChatAppliction.registerService?.send(jsondata.toString(),false)

            }
        }

    }
}
