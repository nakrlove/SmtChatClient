package sock

import android.os.RemoteException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import kotlin.jvm.Throws

//class SocketClient : Serializable {
class SocketClient(port: Int) {

    lateinit var socket: Socket
    lateinit var inputStream: InputStream
    lateinit var outputStream: OutputStream

    init{
        connect(port)
    }


    fun connect(port: Int) {
        Thread {
            try {
                socket = Socket("10.144.243.185", port)
                outputStream = socket?.getOutputStream()
                inputStream = socket?.getInputStream()
            } catch (e: Exception) {
                println("SM_CHAT socket connect exception start!!")
                println("SM_CHAT e: $e")
            }
        }.start()
    }

    @Throws(RemoteException::class)
    fun sendData(data: String) {
        outputStream?.write(
            (data + "\n").toByteArray(Charsets.UTF_8)
            //            (data +"\r\n").toByteArray(Charsets.UTF_8)
        )
    }

    fun flush() {
        outputStream?.flush()
    }

    @Throws(RemoteException::class, SocketException::class)
    fun read(callback: (res: String) -> Unit): Boolean {
        var isRead = false
        if (inputStream?.available()!! > 0) {
            isRead = true
        }

        inputStream?.bufferedReader(Charsets.UTF_8)?.forEachLine {
            callback.invoke(it)
        }

        return isRead
    }

    fun socketClose() {

        if(socket.isConnected) {
            socket?.close()
            outputStream?.close()
            inputStream?.close()
        }

        println(" close Client Connect ################")
    }

}

