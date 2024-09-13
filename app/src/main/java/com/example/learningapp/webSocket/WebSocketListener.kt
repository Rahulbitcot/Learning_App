package com.example.learningapp.webSocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketListener : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d("webSocket" , "Hello 1")
    }

    override fun onMessage(webSocket: WebSocket, text : String) {
        super.onMessage(webSocket, text)
        Log.d("webSocket" , "Response :$text")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        webSocket.close(1000 , reason)
        Log.d("webSocket", "onClosed: $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d("webSocket", "onClosing: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d("webSocket" , "error :$t")
    }
}