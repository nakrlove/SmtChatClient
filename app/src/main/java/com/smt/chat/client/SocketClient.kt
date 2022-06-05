package sock

import android.os.RemoteException
import com.smt.chat.service.ChatAppliction.Companion.callback
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import kotlin.jvm.Throws

typealias CallBack =  (res: String) -> Unit
//class SocketClient : Serializable {
class SocketClient(port: Int,callback: CallBack) {

    var socket: Socket? = null
//    var socket: Socket = Socket("172.30.7.182", 6789)

//    val socket: Socket by lazy {
//        Socket("172.30.7.182", 6789)
//    }
    var inputStream: InputStream?   = null
    var outputStream: OutputStream? = null

    init{
        println("SM_CHAT INIT CALL ======")
        connect(port,callback)
    }


    fun connect(port: Int,callback: CallBack) {
        Thread {
            try {
                socket = Socket("192.168.0.3", port)
                outputStream = socket?.getOutputStream()
                socket?.let{
                    read(callback)
                }
            } catch (e: Exception) {
                println("SM_CHAT SocketClient socket connect exception start!!")
                println("SM_CHAT SocketClient e: $e")
            }
        }.start()
    }



    @Throws(RemoteException::class)
    fun sendData(data: String){

//        socket?.let{
//            if(socket!!.isClosed) {
//                socket = Socket("192.168.0.3", 6789)
//            }
//        } //  socket = Socket("192.168.0.3", 6789)

        outputStream?.write(
            (data + "\n").toByteArray(Charsets.UTF_8)
            //            (data +"\r\n").toByteArray(Charsets.UTF_8)
        )
        outputStream?.flush()

    }

    fun flush(){
        outputStream?.flush()
    }

    @Throws(RemoteException::class, SocketException::class)
    fun read(callback: (res: String) -> Unit): Boolean {
        var isRead = true

        socket?.let{

            if(socket!!.isClosed) {
                println("SM_CHAT  isClosed  $$$$$$$$$$$")
                println("SM_CHAT  isClosed  $$$$$$$$$$$")
                println("SM_CHAT  isClosed  $$$$$$$$$$$")
              return false
            }
           //  socket = Socket("192.168.0.3", 6789)
            inputStream = socket?.getInputStream()
            if (inputStream?.available()!! > 0) {
                isRead = true
            }

            inputStream?.bufferedReader(Charsets.UTF_8)?.forEachLine {
                callback.invoke(it)
            }
        }

        return isRead
    }

    fun socketClose() {

        if(socket!!.isConnected) {
            println("SM_CHAT SocketClient close called ################")
            println("SM_CHAT SocketClient close called ################")
            println("SM_CHAT SocketClient close called ################")
            socket?.close()
            outputStream?.close()
            inputStream?.close()
        }

        println("SM_CHAT SocketClient close Client Connect ################")
    }

}

