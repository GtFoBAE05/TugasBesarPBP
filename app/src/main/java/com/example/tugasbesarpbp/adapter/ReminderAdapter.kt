package com.example.tugasbesarpbp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.R
import com.example.tugasbesarpbp.models.Reminder

class ReminderAdapter(private var data: List<Reminder>, private var listener : OnAdapterListener): RecyclerView.Adapter<ReminderAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderAdapter.viewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.rv_reminder, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReminderAdapter.viewHolder, position: Int) {
        val currentItem = data[position]
        holder.txtReminderName.text = currentItem.name
        holder.txtReminderDate.text = currentItem.date

        holder.itemView.setOnClickListener {
            listener.onClick(currentItem)
        }
    }

    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtReminderName: TextView = itemView.findViewById(R.id.reminderNameTv)
        val txtReminderDate: TextView = itemView.findViewById(R.id.reminderDateTv)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnAdapterListener{
        fun onClick(reminder:Reminder)
    }

    fun setList(id : Int, reminder: Array<Reminder>){
        var result = ArrayList<Reminder>()

        for (element in reminder){
            if(element.userId==id){
                result.add(element)
            }
        }

        data = result.toList()
        notifyDataSetChanged()

    }

}