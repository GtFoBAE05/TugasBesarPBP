package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.tugasbesarpbp.databinding.FragmentAddPocketBinding
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class addPocketFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }

    lateinit var binding: FragmentAddPocketBinding

    lateinit var pocketName: EditText
    lateinit var pocketBalance: EditText

    lateinit var btnAddPocket: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAddPocketBinding.inflate(inflater, container, false)
        val view= binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPocket=binding.btnAddPocket

        btnAddPocket.setOnClickListener{
            val idUser= requireActivity().intent.getIntExtra("idLogin",0)

            pocketName=binding.etPocketName
            pocketBalance=binding.etPocketBalance

            val name=pocketName.text.toString()
            val balance : Double= pocketBalance.text.toString().toDouble()

            if(name.isNotEmpty() && balance!=null){
                CoroutineScope(Dispatchers.IO).launch {
                    db.pocketDao().addPocket(pocket(0,idUser, name,balance))
                }
            }

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketFragment()).commit()
        }
    }
}