package com.example.tugasbesarpbp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.entity.Pocket
import org.w3c.dom.Text

class rvPocketAdapter(private val data:List<Pocket>): RecyclerView.Adapter<rvPocketAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.rv_pocket, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem= data[position]
        holder.txtPocketName.text=currentItem.pocketName
        holder.txtPocketBalance.text="$"+currentItem.pocketBalance
    }

    override fun getItemCount(): Int {
        return data.size
    }



    class viewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtPocketName:TextView= itemView.findViewById(R.id.txtPocketTitle)
        val txtPocketBalance: TextView= itemView.findViewById(R.id.txtPocketBalance)
    }


}