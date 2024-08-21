package com.example.learningapp.roomDb.model
 import androidx.room.Entity
 import androidx.room.PrimaryKey

@Entity
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val name :String,
    val phoneNumber : String
)