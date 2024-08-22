package com.example.learningapp.retrofit.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "users")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,

    val dob: Dob,
    val email: String,
    val gender: String,
    val location: Location,
    val name: Name,
    val phone: String,

//    val picture: Picture,
//    val registered: Registered ,
//    val id: Id,
//    val cell: String,
//    val nat: String,
//    val login: Login,

)