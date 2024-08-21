package com.example.learningapp.roomDb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.learningapp.roomDb.model.Contacts

@Dao
interface ContactsDao {

     @Insert
     suspend fun insertContact(contacts : Contacts)

     @Update
     suspend fun updateContact(contacts : Contacts)

     @Delete
     suspend fun deleteContact(contacts : Contacts)

     @Query( "select * from Contacts ")
     fun getContact() : LiveData<List<Contacts>>

}