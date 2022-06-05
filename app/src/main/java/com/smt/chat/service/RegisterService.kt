package com.smt.chat.service

import android.app.Service
import android.content.Intent
import android.os.*

class RegisterService: Service() {

    // 1 Socket 연결
    // 2 Socket Connected 상태 유지
//    var client: SocketClient? = ChatAppliction.client
//    val client: SocketClient by lazy {
//        SocketClient(6789, {
//                val num: Int = callbackList.beginBroadcast()
//                for (i in 0 until num) {
//                    callbackList.getBroadcastItem(i).receive(it)
//                    callbackList.finishBroadcast()
//                }
//        })
//    }


//
//    val client: SocketClient =  SocketClient(6789, {
//        val num: Int = callbackList.beginBroadcast()
//        for (i in 0 until num) {
//            callbackList.getBroadcastItem(i).receive(it)
//            callbackList.finishBroadcast()
//        }
//    })




    companion object{
        val callbackList = RemoteCallbackList<IClientCallback>()
        var thread:Thread? = null

//        val client: SocketClient  by lazy{
//            SocketClient(6789, {
//                val num: Int = callbackList.beginBroadcast()
//                for (i in 0 until num) {
//                    callbackList.getBroadcastItem(i).receive(it)
//                    callbackList.finishBroadcast()
//                }
//            })
//        }


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
        override fun send(json: String?,isConnected: Boolean ) {
            ChatAppliction.getClient()?.let {
                while(it.outputStream == null){
                    println(" socket waiting ========")
                }

                println(" thrad run @@@@@@@@@@@@@@")
                thread = Thread {
                    it.sendData(json!!)
                }
                thread?.start()
            }
        }

    }


    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

}