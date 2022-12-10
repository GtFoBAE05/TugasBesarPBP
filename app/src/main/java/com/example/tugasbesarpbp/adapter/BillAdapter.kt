package com.example.tugasbesarpbp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tugasbesarpbp.R
import com.example.tugasbesarpbp.billDetailFragment
import com.example.tugasbesarpbp.databinding.FragmentBillBinding
import com.example.tugasbesarpbp.homeActivity
import com.example.tugasbesarpbp.models.Bill
import com.example.tugasbesarpbp.pocketRoom.pocket
import com.example.tugasbesarpbp.rvPocketAdapter
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.fragment_add_bill.view.*
import kotlinx.android.synthetic.main.rv_bill.view.*
import java.util.*

class BillAdapter (private var data: List<Bill>, context: Context, private val listener: OnAdapterListener): RecyclerView.Adapter<BillAdapter.ViewHolder>(), Filterable {
    private val context:Context
    private var billResult : MutableList<Bill>
    private var id:Int? = null

    init{
        billResult = ArrayList(data)
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_bill,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return billResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = billResult[position]

        holder.tvName.text=  currentItem.name
        holder.tvDate.text= currentItem.date
        holder.tvAmount.text= "$" + currentItem.amount.toString()

        holder.cvBill.setOnClickListener {
//            val i = Intent(context, billDetailFragment::class.java)
//            i.putExtra("billId", currentItem.id)
//            println("aaa"+i.getIntExtra("billId",0))
//            context.startActivity(i)
            //val aa= homeActivity().changeFragment(billDetailFragment())
            //billDetailFragment().requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,billDetailFragment()).commit()
            listener.onClick(currentItem)

        }
    }




    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvName: TextView = itemView.findViewById(R.id.tvBillName)
        var tvDate: TextView = itemView.findViewById(R.id.tvBillDate)
        var tvAmount: TextView = itemView.findViewById(R.id.tvBillAmount)
        var cvBill : CardView = itemView.findViewById(R.id.cvBill)
    }

    fun setBillList(data: Array<Bill>){
        this.data = data.toList()
        billResult = data.toMutableList()
    }

    fun setId(x : Int){
        id = x
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSequenceString= charSequence.toString()
                val filtered: MutableList<Bill> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){

                    for(element in data){
                        if(element.userId == id){
                            //println(element.name)
                            filtered.add(element)
                        }
                    }
//                    println("tipe data" + result::class.simpleName)

 //                   filtered.addAll(data)
                }else{
                    for (element in data){
                        if(element.userId == id){
                            if(element.name.lowercase(Locale.getDefault()).contains(charSequenceString.lowercase(
                                    Locale.getDefault()
                                ))) filtered.add(element)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values= filtered
//               println("hasil" + filterResults::class.simpleName)
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                billResult.clear()
                billResult.addAll((p1?.values as List<Bill>))
                notifyDataSetChanged()
            }
        }
    }

    interface OnAdapterListener{
        fun onClick(bill:Bill)
    }

}