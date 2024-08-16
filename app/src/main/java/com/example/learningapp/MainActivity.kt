package com.example.learningapp

import com.example.learningapp.recyclerView.RecyclerViewActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.activityLifeCycle.ActivityLifeCycle
import com.example.learningapp.databinding.ActivityMainBinding
import com.example.learningapp.fragmentLifeCycle.FragmentLifeCycle
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            onBtnClick(binding.btRecyclerView ,RecyclerViewActivity::class.java )
            onBtnClick(binding.btActivityLifecycle ,ActivityLifeCycle::class.java )
            onBtnClick(binding.btFragmentLifeCycle ,FragmentLifeCycle::class.java )


    }

    private fun onBtnClick(btn : MaterialButton, targetActivity: Class<out AppCompatActivity>){
        btn.setOnClickListener{
            val intent = Intent(this , targetActivity  )
            startActivity(intent)
        }
    }
}