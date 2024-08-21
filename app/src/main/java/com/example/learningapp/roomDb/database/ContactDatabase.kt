package com.example.learningapp.roomDb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learningapp.roomDb.dao.ContactsDao
import com.example.learningapp.roomDb.model.Contacts

@Database (entities = [Contacts::class] , version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}