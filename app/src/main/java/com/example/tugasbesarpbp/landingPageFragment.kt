package com.example.tugasbesarpbp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.entity.Pocket
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class landingPageFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }
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




        CoroutineScope(Dispatchers.IO).launch {
            val idUser= requireActivity().intent.getIntExtra("idLogin",0)

            val result= db.pocketDao().getPocket(idUser)
            val adapter: rvPocketAdapter = rvPocketAdapter(result)
            val layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            val rvPocket: RecyclerView= view.findViewById(R.id.rv_pocket)

            rvPocket.layoutManager=layoutManager

            rvPocket.setHasFixedSize(true)

            rvPocket.adapter=adapter
        }




    }




}