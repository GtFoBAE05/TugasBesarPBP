package com.example.tugasbesarpbp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.models.Pocket
import com.example.tugasbesarpbp.pocketRoom.pocket

class rvPocketAdapter(private var data:List<Pocket>, private val listener: OnAdapterListener ): RecyclerView.Adapter<rvPocketAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.rv_pocket, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem= data[position]
        holder.txtPocketName.text=currentItem.name
        holder.txtPocketBalance.text="$"+currentItem.balance

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
        fun onClick(pocket: Pocket)
    }

    fun setList(id : Int, pocket: Array<Pocket>){
        var result = ArrayList<Pocket>()

        for (element in pocket){
            if(element.userId==id){
                result.add(element)
            }
        }

        data = result.toList()
        notifyDataSetChanged()

    }


}