package com.example.learningapp.roomDb.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.learningapp.databinding.ActivityRoomDbBinding
import com.example.learningapp.roomDb.adapter.RoomDbAdapter
import com.example.learningapp.roomDb.database.ContactDatabase
import com.example.learningapp.roomDb.model.Contacts
import com.example.learningapp.roomDb.viewModel.RoomViewModel
import com.example.learningapp.roomDb.viewModel.RoomViewModelFactory
import kotlinx.coroutines.launch

class RoomDbActivity : AppCompatActivity() {
    lateinit var database  :ContactDatabase
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var roomDbAdapter: RoomDbAdapter
    private lateinit var binding : ActivityRoomDbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(applicationContext , ContactDatabase::class.java ,
            "Contact Database").build()

        lifecycleScope.launch {
            database.contactsDao().deleteAllContact()

            database.contactsDao().insertContact(
                  Contacts(0 , "Rahul" , "788524953"))
          }

        database.contactsDao().getContact().observe(this) {
            Log.d("RoomData", it.toString())
        }
        roomViewModel = ViewModelProvider(this, RoomViewModelFactory(database)).get(RoomViewModel::class.java)
        randomUserRecyclerView()

    }

    private fun randomUserRecyclerView() {
        roomDbAdapter = RoomDbAdapter(emptyList())
        binding.roomDbRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.roomDbRecyclerView.adapter=roomDbAdapter

        roomViewModel.contacts.observe(this) { results ->
            results?.let {
                roomDbAdapter.updateData(it)
            }
        }
    }
}