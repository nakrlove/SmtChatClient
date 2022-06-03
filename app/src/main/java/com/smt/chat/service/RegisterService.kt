package com.smt.chat.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.smt.chat.cost.KConst
import org.json.JSONObject
import sock.SocketClient
import java.net.SocketException

class RegisterService: Service() {

    // 1 Socket 연결
    // 2 Socket Connected 상태 유지
    var client: SocketClient? = ChatAppliction.client

    var isConnect = true
    companion object{
        val callbackList = RemoteCallbackList<IClientCallback>()
        var thread:Thread? = null
    }

    private val binder = object : IRegisterService.Stub() {

        @Throws(RemoteException::class)
        override fun registerCallback(listener: IClientCallback) {
            callbackList.register(listener)
        }

        @Throws(RemoteException::class)
        override fun unregisterCallback(listener: IClientCallback) {
            callbackList.unregister(listener)
        }

        ///메세지 전달요청
        override fun send(json: String?) {
            callbackList.beginBroadcast()
            client?.let {
                thread = Thread {

                    it.sendData(json!!)
                    it.flush()

                    while (thread?.isInterrupted() == false) {

                            try {
                                it?.read() { res ->
                                    val num: Int = callbackList.beginBroadcast()
                                    for (i in 0 until num) {
                                        callbackList.getBroadcastItem(i).receive(res)
                                        callbackList.finishBroadcast()
                                    }
                                }
                            }catch( e: SocketException ){
                                println(" Exception call =====================")
                                thread?.interrupt()
                            }
                    }
                }
                thread?.start()
            }

            callbackList.finishBroadcast()
        }

        /// 서버에 연결종료처리 요청
        override fun disconnect() {

            val jsondata = JSONObject().apply {
                put(KConst.MESSAGE_DATA, "QUIT")
                put(KConst.NICKNAME_CHK, "N")
            }

            send(jsondata.toString())
            client?.socketClose()
//            callbackList.finishBroadcast()
//            client?.socketClose()
        }


    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

}