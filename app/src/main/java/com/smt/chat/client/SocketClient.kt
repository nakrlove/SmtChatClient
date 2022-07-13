package sock

import android.os.RemoteException
import com.smt.chat.service.ChatAppliction.Companion.callback
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import kotlin.jvm.Throws

typealias CallBack =  (res: String) -> Unit

class SocketClient(port: Int,callback: CallBack) {
//git commit테스트용
    var socket: Socket? = null

    var inputStream: InputStream?   = null
    var outputStream: OutputStream? = null

    init{
        connect(port,callback)
    }


    // port : socekt port
    // callback : 다른사용자로 부터받은 메세지를 화면단에 넘겨줄 callback함수
    fun connect(port: Int,callback: CallBack) {
        Thread {
            try {
//                socket = Socket("192.168.0.3", port)
//                socket = Socket("172.20.10.3", port)
//                socket = Socket("10.144.243.185", port)
                socket = Socket("172.30.7.182", port)

                //Chating 메세지 전송을위해 output 초기화
                outputStream = socket?.getOutputStream()
                inputStream = socket?.getInputStream()
                //다른 사용자로부터 메세지 받기
                socket?.let{
                    read(callback)
                }
            } catch (e: Exception) {
                println("SM_CHAT SocketClient e: $e")
            }
        }.start()
    }



    //Chating 메세지전송
    @Throws(RemoteException::class)
    fun sendData(data: String){

        outputStream?.write(
            (data + "\n").toByteArray(Charsets.UTF_8)
            //            (data +"\r\n").toByteArray(Charsets.UTF_8)
        )
        outputStream?.flush()

    }


    //다른 사용자로부터 받은메세지
    @Throws(RemoteException::class, SocketException::class)
    fun read(callback: (res: String) -> Unit): Boolean {
        var isRead = true

        socket?.let{

            //socket이 끊어진경우
            if(socket!!.isClosed) {
              return false
            }

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
            socket?.close()
            outputStream?.close()
            inputStream?.close()
        }

        println("SM_CHAT SocketClient close Client Connect ################")
    }

}

