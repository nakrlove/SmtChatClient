package com.smt.chat.service

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import com.smt.chat.uiif.UIInterface
import org.json.JSONObject
import sock.SocketClient


//https://dev-ahn.tistory.com/160
class ChatAppliction: Application() {

    lateinit var registerService: IRegisterService
    companion object {

        var count = 0
        val handler:Handler = Handler(Looper.getMainLooper())
        var uCallback: UIInterface? = null


        lateinit var instance: ChatAppliction
        fun setCallback(uCallback: UIInterface){
            this.uCallback = uCallback
        }

        val client: SocketClient by lazy {
            SocketClient(6789)
        }




        // 서버에서 응답처리
        val callback = object : IClientCallback.Stub() {
            override fun disconnect(json: String?) {
            }

            override fun receive(json: String?) {
                val json = JSONObject(json)
                uCallback?.execute(json)
            }
        }



    }

    val connection = object : ServiceConnection {
        // Called when the connection with the service is established
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            registerService = IRegisterService.Stub.asInterface(service)
            handler.post{
                registerService?.let{
                    try{
                        it.registerCallback(callback)
                    }catch(e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }

        // Called when the connection with the service disconnects unexpectedly
        override fun onServiceDisconnected(className: ComponentName) {
//        Log.e(TAG, "Service has unexpectedly disconnected")
            println("SM_CHAT ChatAppliction onServiceDisconnected ##############################")
        }
    }

    override fun onCreate() {
        super.onCreate()

//        unbindBinderExtendedService()
        instance = this

        println("SM_CHAT ChatAppliction onCreate ##############################")
        val appIntent = Intent(instance.applicationContext, RegisterService::class.java)
        instance.applicationContext.bindService(appIntent, connection, BIND_AUTO_CREATE)

    }

    fun disconnect(){
        println("SM_CHAT ChatAppliction disconnect ##############################")
//        client?.socketClose()
        unbindBinderExtendedService()
    }

    fun unbindBinderExtendedService() {
        unbindService(connection)
    }

}