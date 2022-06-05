package com.smt.chat.service

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.widget.Toast
import com.smt.chat.MainActivity
import com.smt.chat.cost.KConst
import org.json.JSONObject


//https://kjwsx23.tistory.com/187 참조
class ConnectionService: Service()  {

//    protected val client: SocketClient by lazy {
//        println("SM_CHAT SocketClient init #########################")
//        SocketClient(6789)
//    }


    companion object{
        const val MSG_SAY_HELLO = 1
    }


//    val handler:Handler = Handler(Looper.getMainLooper())
//
//    // 서버에서 응답처리
//    val callback = object : IClientCallback.Stub() {
//
//        override fun receive(json: String?) {
//            val json = JSONObject(json)
//            json?.let{
//                if( it.get(KConst.NICKNAME_CHK) != "Y"){
//                    runOnUiThread {
//                        Toast.makeText(this@BaseActivity,"${json}", Toast.LENGTH_LONG).show()
//                    }
//                    return@let
//                }
//
//                Intent(this@BaseActivity, MainActivity::class.java).apply {
//                    startActivity(this)
//                }
//            }
//        }
//    }


//    protected var registerService: IRegisterService? = null
//
//    val connection = object : ServiceConnection {
//        // Called when the connection with the service is established
//        override fun onServiceConnected(className: ComponentName, service: IBinder) {
//            registerService = IRegisterService.Stub.asInterface(service)
//            handler.post{
//                registerService?.let{
//                    try{
//                        it.registerCallback(callback)
//                    }catch(e: Exception){
//                        e.printStackTrace()
//                    }
//                }
//            }
//        }
//
//        // Called when the connection with the service disconnects unexpectedly
//        override fun onServiceDisconnected(className: ComponentName) {
////        Log.e(TAG, "Service has unexpectedly disconnected")
//            println("SM_CHAT ChatAppliction onServiceDisconnected ##############################")
//        }
//    }




    private lateinit var mMessenger: Messenger
    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO ->
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show()
                else -> super.handleMessage(msg)
            }
        }
    }
    private val binder = object : IConnectionService.Stub(){
//        override fun basicTypes(
//            anInt: Int,
//            aLong: Long,
//            aBoolean: Boolean,
//            aFloat: Float,
//            aDouble: Double,
//            aString: String?
//        ) {
//            TODO("Not yet implemented")
//        }

//        override fun getStatus(): Int {
//            return 0
//        }
        override fun connect() {

            println("SM_CHAT connect  6789 ##############################")
//            try {
//                client.connect(6789)
//            }catch( e: Exception ){
//                println("SM_CHAT connect  Exception ")
//                e.printStackTrace()
//                println("SM_CHAT connect  Exception ")
//            }


        }
        override fun disconnect() {
//            client.closeConnect()
        }

        override fun send(sendData: String): String? {

            println(" SM_CHAT send called @@@@@@@@@@@@@@@@@@@@@@@@@")

//            var recvData: String? = null
////            Thread {
//                client?.let {
//
//                    it.sendData(sendData)
//                    it.flush()
//                    while (true) {
//
//                        it.read() { res ->
////                                val respObj = JSONObject(it)
////                                receive(respObj.toString())
//                            recvData = res
//                            println(" SM_CHAT 1 recvData = ${recvData}")
//
//                        }
//                    }
//                }
//            }.start()
//            println(" SM_CHAT 2 recvData = ${recvData}")
//            return recvData
            return null
        }

//        override fun receive(recevData: com.smt.chat.service.JSONObject?) {
//            TODO("Not yet implemented")
//        }

        override fun receive(): String? {

            println("SM_CHAT receive called @@@@@@@@@@@@@@@@@@@@@@@@@")
            println("SM_CHAT receive called @@@@@@@@@@@@@@@@@@@@@@@@@")
            println("SM_CHAT receive called @@@@@@@@@@@@@@@@@@@@@@@@@")
            var receiveData: String? = null
//            Thread {
//                client?.let {
//                    WHILE@ while (true)  {
//
//                        val receve =  it.read()  { res ->
//                            /*res?.let {
//
//                                val respObj = JSONObject(res)
////                            callback(respObj)
////                                val handlerObj = handlerResp.obtainMessage(handerType)
////                                handlerObj.obj = respObj
////                                handlerResp.sendMessage(handlerObj)
//
//                            }
//*/
//                            receiveData = res
//                            println("SM_CHAT Recv = ${receiveData}")
//                        }
//                        println("SM_CHAT Recv = break")
//                        if( receve ) break
//                    }
//                }
//            }.start().run {
//                println("SM_CHAT Recv = run")
//                return receiveData
//            }

            return receiveData
        }
    }

//
    override fun onCreate() {
        super.onCreate()
        println("SM_CHAT ConnectionService , onCreate() -> connect() ")
        binder?.connect()
        println("SM_CHAT ConnectionService , onCreate()")
    }

    override fun onStartCommand( intent: Intent , flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        println("SM_CHAT ConnectionService , onStartCommand()")

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        // Return the interface
        println("SM_CHAT ConnectionService , onBind()")

        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
//        return binder
    }

    override fun onUnbind(intent: Intent): Boolean{
        println("SM_CHAT ConnectionService, onUnbind()")
        return super.onUnbind(intent)
    }

    override fun onDestroy(){
        println("SM_CHAT ConnectionService, onDestroy()")
        super.onDestroy()
    }


    inner class ChatBinder : Binder() {
        val service: ConnectionService
            get() = this@ConnectionService
    }



}