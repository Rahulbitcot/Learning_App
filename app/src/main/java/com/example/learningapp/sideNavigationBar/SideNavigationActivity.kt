package com.example.learningapp.sideNavigationBar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivitySideNavigationBinding

class SideNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySideNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openDrawer()
        itemClick()
    }

    private fun itemClick() {
        binding.sideNavigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.id_shared -> {
                    Toast.makeText(this, "Clicked On \"shared with me\" item" ,Toast.LENGTH_SHORT).show()
                }
                R.id.id_recent -> {
                    Toast.makeText(this, "Clicked On \"Recent\" item" ,Toast.LENGTH_SHORT).show()
                }

                R.id.id_trash -> {
                    Toast.makeText(this, "Clicked On \"Trash\" item" ,Toast.LENGTH_SHORT).show()
                }

                R.id.id_setting -> {
                    Toast.makeText(this, "Clicked On \"Setting\" item" ,Toast.LENGTH_SHORT).show()
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