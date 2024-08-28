package com.example.learningapp.sideAndBottomNavigationBar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivitySideNavigationBinding

class SideAndBottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySideNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openDrawer()
        sideNavigationClick()
        bottomNavigationClick()
    }

    private fun bottomNavigationClick() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show()
                    binding.title.text="Home"
                    true
                }
                R.id.chat -> {
                    Toast.makeText(this, "Chat Selected", Toast.LENGTH_SHORT).show()
                    binding.title.text="Chat"

                    true
                }
                R.id.call -> {
                    Toast.makeText(this, "Call Selected", Toast.LENGTH_SHORT).show()
                    binding.title.text="Call"

                    true
                }
                else -> false
            }
        }
    }
    private fun sideNavigationClick() {
        binding.sideNavigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.id_shared -> {
                    Toast.makeText(this, "Clicked On \"shared with me\" item" ,Toast.LENGTH_SHORT).show()
                    binding.title.text="shared with me"

                }
                R.id.id_recent -> {
                    Toast.makeText(this, "Clicked On \"Recent\" item" ,Toast.LENGTH_SHORT).show()
                    binding.title.text="Recent"
                }

                R.id.id_trash -> {
                    Toast.makeText(this, "Clicked On \"Trash\" item" ,Toast.LENGTH_SHORT).show()
                    binding.title.text="Trash"

                }
                R.id.id_setting -> {
                    Toast.makeText(this, "Clicked On \"Setting\" item" ,Toast.LENGTH_SHORT).show()
                    binding.title.text="Setting"
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun openDrawer() {
        binding.btMenu.setOnClickListener{
            binding.drawerLayout.open()
        }
    }
}