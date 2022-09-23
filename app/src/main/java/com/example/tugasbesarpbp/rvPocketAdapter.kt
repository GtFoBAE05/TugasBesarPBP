package com.example.tugasbesarpbp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.entity.Pocket
import com.example.tugasbesarpbp.pocketRoom.pocket
import org.w3c.dom.Text

class rvPocketAdapter(private val data:List<pocket>, private val listener: OnAdapterListener ): RecyclerView.Adapter<rvPocketAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.rv_pocket, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem= data[position]
        holder.txtPocketName.text=currentItem.pocketName
        holder.txtPocketBalance.text="$"+currentItem.pocketBalance

        holder.itemView.setOnClickListener {
            listener.onClick(currentItem)
        }


//        holder.itemView.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(p0: View?) {
//
//                val activity=p0!!.context as AppCompatActivity
//                val fragment=updateProfileFragment()
//                val intent = Intent()
//
//                intent.putExtra("userId", 1)
//
//                activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,pocketDetailFragment()).commit()
//            }
//        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtPocketName:TextView= itemView.findViewById(R.id.txtPocketTitle)
        val txtPocketBalance: TextView= itemView.findViewById(R.id.txtPocketBalance)
    }

    interface OnAdapterListener{
        fun onClick(pocket: pocket)
    }


}