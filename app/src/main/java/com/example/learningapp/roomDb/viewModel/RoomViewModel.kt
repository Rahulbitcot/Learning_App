package com.example.learningapp.roomDb.viewModel

import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import com.example.learningapp.roomDb.database.ContactDatabase
import com.example.learningapp.roomDb.model.Contacts


class RoomViewModel (private val contactsDatabase: ContactDatabase): ViewModel() {
    val contacts: LiveData<List<Contacts>> = contactsDatabase.contactsDao().getContact()

}