package com.example.learningapp.webSocket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityWebSocketBinding
import okhttp3.*

class WebSocketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebSocketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityWebSocketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val client = OkHttpClient()
        binding.btnStartConnection.setOnClickListener{
           val request : Request = Request.Builder().url("wss://echo.websocket.org").build()
           val listener  = WebSocketListener()
              val ws : WebSocket = client.newWebSocket(request, listener)
              ws.close(1000,"Work Done")
        }
    }
}