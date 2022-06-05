package com.smt.chat.service

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.core.app.ActivityCompat
import com.smt.chat.service.RegisterService.Companion.callbackList
import com.smt.chat.uiif.UIInterface
import org.json.JSONObject
import sock.SocketClient
import kotlin.system.exitProcess


//https://dev-ahn.tistory.com/160
class ChatAppliction: Application() {

    val client: SocketClient by lazy {
        SocketClient(6789, {
            val num: Int = callbackList.beginBroadcast()
            for (i in 0 until num) {
                callbackList.getBroadcastItem(i).receive(it)
                callbackList.finishBroadcast()
            }
        })
    }

    companion object {

        lateinit var registerService: IRegisterService
        lateinit var instance: ChatAppliction

        val handler:Handler = Handler(Looper.getMainLooper())
        var uCallback: UIInterface? = null
        fun setCallback(uCallback: UIInterface){
            this.uCallback = uCallback
        }

        fun getClient(): SocketClient {
            return instance.client
        }

        // 접속사용자 정보
        var userInfo : JSONObject? = null
        set(value){
            field = value
        }

        // 서버에서 응답처리
        val callback = object : IClientCallback.Stub() {
            override fun receive(json: String?) {
                val json = JSONObject(json)
                uCallback?.execute(json)
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
                registerService?.let{
                    it.unregisterCallback(callback)
                    println("SM_CHAT ChatAppliction onServiceDisconnected ##############################")
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        println("SM_CHAT ChatAppliction onCreate ##############################")
        val appIntent = Intent(this, RegisterService::class.java)
        bindService(appIntent, connection, BIND_AUTO_CREATE)

    }


    //App종료
    fun disconnect(activity: Activity){
        println("SM_CHAT ChatAppliction disconnect ##############################")
        unbindService(connection)
        ActivityCompat.finishAffinity(activity)
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }

}