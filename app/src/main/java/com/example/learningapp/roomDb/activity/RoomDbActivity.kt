package com.example.learningapp.roomDb.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.learningapp.databinding.ActivityRoomDbBinding
import com.example.learningapp.roomDb.database.ContactDatabase
import com.example.learningapp.roomDb.model.Contacts
import kotlinx.coroutines.launch

class RoomDbActivity : AppCompatActivity() {
    lateinit var database  :ContactDatabase
    private lateinit var binding : ActivityRoomDbBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(applicationContext , ContactDatabase::class.java ,
            "Contact Database").build()

        lifecycleScope.launch {
              database.contactsDao().insertContact(
                  Contacts(0 , "Rahul" , "788524953"))
          }

        database.contactsDao().getContact().observe(this) {
            Log.d("RoomData", it.toString())
        }

    }
}