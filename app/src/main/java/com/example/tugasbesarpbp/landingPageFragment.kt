package com.example.tugasbesarpbp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.entity.Pocket


class landingPageFragment : Fragment() {
    private lateinit var tvUsername:TextView
    var myTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvUsername= inflater.inflate(R.layout.fragment_landing_page, container, false).findViewById(R.id.txtUserName);



        return inflater.inflate(R.layout.fragment_landing_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val adapter: rvPocketAdapter = rvPocketAdapter(Pocket.listOfPocket)
        val rvPocket: RecyclerView= view.findViewById(R.id.rv_pocket)

        rvPocket.layoutManager=layoutManager

        rvPocket.setHasFixedSize(true)

        rvPocket.adapter=adapter



    }


}