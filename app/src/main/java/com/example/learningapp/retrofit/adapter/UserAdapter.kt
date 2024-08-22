package com.example.learningapp.retrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.R
import com.example.learningapp.retrofit.models.Result

class UserAdapter(private var user : List<Result>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.findViewById(R.id.txtView_Name)
        val email : TextView = view.findViewById(R.id.txtView_Email)
        val dob : TextView = view.findViewById(R.id.txtView_Dob)
        val location : TextView = view.findViewById(R.id.txtView_Location)
        val phone : TextView = view.findViewById(R.id.txtView_Phone)
        val gender : TextView = view.findViewById(R.id.txtView_Gender)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.random_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = user[position]
        holder.name.text = result.name.first
        holder.gender.text = result.gender
        holder.email.text = result.email
        holder.dob.text = result.dob.date
        holder.location.text = result.location.city
        holder.phone.text = result.phone
    }

    override fun getItemCount(): Int {
        return  user.size
    }

    fun updateData(newResults: List<Result>) {
        user = newResults
        notifyDataSetChanged()
    }


}