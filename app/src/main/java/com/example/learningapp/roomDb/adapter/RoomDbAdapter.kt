package com.example.learningapp.roomDb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.R
import com.example.learningapp.roomDb.model.Contacts


class RoomDbAdapter(private var contacts: List<Contacts>) :
    RecyclerView.Adapter<RoomDbAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.findViewById(R.id.txtView_Name)
        val id : TextView = view.findViewById(R.id.txtView_Id)
        val phoneNumber : TextView = view.findViewById(R.id.txtView_PhoneNumber)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_db_item_view, parent, false)
        return ViewHolder(view)
    }

    fun updateData(newContacts: List<Contacts>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contacts[position]
        holder.name.text = item.name
        holder.id.text = item.id.toString()
        holder.phoneNumber.text = item.phoneNumber

    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}