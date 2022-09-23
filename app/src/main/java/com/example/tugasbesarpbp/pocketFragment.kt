package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class pocketFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }

    lateinit var btn: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pocket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val idUser= requireActivity().intent.getIntExtra("idLogin",0)

            var result= db.pocketDao().getPocket(idUser)


            val adapter: rvPocketAdapter = rvPocketAdapter(result)
            val layoutManager= GridLayoutManager(requireContext(),2)
            val rvPocket: RecyclerView = view.findViewById(R.id.rvPocketPage)

            rvPocket.layoutManager=layoutManager

            rvPocket.setHasFixedSize(true)

            rvPocket.adapter=adapter




        }

        btn=view.findViewById(R.id.addPocketButton)

        btn.setOnClickListener{

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, addPocketFragment()).commit()
        }
    }
}