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
    lateinit var tvUsername:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_landing_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUsername= view.findViewById(R.id.txtUserName);

        val username=requireActivity().intent.getStringExtra("usernameLogin")

        tvUsername.text=username

        val layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val adapter: rvPocketAdapter = rvPocketAdapter(Pocket.listOfPocket)
        val rvPocket: RecyclerView= view.findViewById(R.id.rv_pocket)

        rvPocket.layoutManager=layoutManager

        rvPocket.setHasFixedSize(true)

        rvPocket.adapter=adapter

    }




}