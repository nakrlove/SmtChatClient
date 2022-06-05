package com.smt.chat

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.smt.chat.cost.KConst
import com.smt.chat.service.ChatAppliction
import org.json.JSONObject

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(" onCreate =========== ")
    }

    override fun onDestroy() {
        super.onDestroy()
        println(" onDestroy isAlive =========== is null")
    }


    fun disconnet(){
        var jsondata = ChatAppliction.userInfo?.apply {
            put(KConst.MESSAGE_DATA, "QUIT")
        }
        ChatAppliction.registerService?.send(jsondata.toString(),true)
    }

    fun sendMsg(nickname: String,msg: String, handlerResp: Handler , handerType: Int) {


        var jsondata = JSONObject().apply {
            put(KConst.MESSAGE_DATA, msg)
            put(KConst.NICKNAME_KEY, nickname)
        }

//        val jsonString = jsondata.toString()
//        Thread {
//            client?.let {
//
//                it.sendData(jsonString)
//                it.flush()
//
//                while (true) {
//
//                    it.read() { res ->
//                        res?.let {
//
//                            val respObj = JSONObject(res)
////                            callback(respObj)
//                            val handlerObj = handlerResp.obtainMessage(handerType)
//                            handlerObj.obj = respObj
//                            handlerResp.sendMessage(handlerObj)
//
//                        }
//
//                    }
//                }
//            }
//        }.start()
    }
}