package com.smt.chat.service

import android.app.Service
import android.content.Intent
import android.os.*

class RegisterService: Service() {

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
        override fun send(json: String?,isConnected: Boolean ) {
            ChatAppliction.getClient()?.let {

                while(it.outputStream == null){
                    // socket 초기화 및 output 초기화 시간지
                }

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