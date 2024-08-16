package com.example.learningapp.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.R

// Custom Adapter For The Recycler View

class CustomAdapter(private val dataSet: Array<ApiData>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idNation: TextView = view.findViewById(R.id.txtView_idNation)
        val nation :TextView = view.findViewById(R.id.txtView_Nation)
        val year :TextView = view.findViewById(R.id.txtView_Year)
        val population :TextView = view.findViewById(R.id.txtView_Population)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.idNation.text = item.idNation
        holder.nation.text = item.nation
        holder.year.text = item.year
        holder.population.text = item.population

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
