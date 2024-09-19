package com.example.learningapp.hiltLibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HiltLibrary : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    @Named("print2")
    lateinit var myRepository: MyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hilt_library)
        Toast.makeText(this, myRepository.getWelcomeMessage(), Toast.LENGTH_SHORT).show()

        val welcomeMessage = viewModel.fetchWelcomeMessage()
        Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()
    }
}