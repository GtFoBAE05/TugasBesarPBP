package com.example.tugasbesarpbp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.tugasbesarpbp.databinding.FragmentPocketBinding
import com.example.tugasbesarpbp.databinding.FragmentPocketDetailBinding
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class pocketDetailFragment : Fragment() {

    val db by lazy { PocketDB(requireContext()) }

    lateinit var binding: FragmentPocketDetailBinding

    private lateinit var etPocketName:EditText
    private lateinit var etPocketBalance:EditText

    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentPocketDetailBinding.inflate(inflater,container,false)
        val view= binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPocketName=binding.editPocketName
        etPocketBalance=binding.editPocketBalance

        var name:String
        var balance:String
        var pocketId= requireActivity().intent.getIntExtra("pocketId",0)

        var result: List<pocket>
        CoroutineScope(Dispatchers.IO).launch {

            result=db.pocketDao().getPocketById(pocketId)

            name=result[0].pocketName.toString()
            balance=result[0].pocketBalance.toString()

            println("test:" + name +"  "+ balance)

            requireActivity().runOnUiThread() {
                etPocketName.setText(name)
                etPocketBalance.setText(balance)
            }

        }

        btnUpdate=binding.buttonUpdatePocketDetailPage
        btnUpdate.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                var pocketId= requireActivity().intent.getIntExtra("pocketId",0)
                var result=db.pocketDao().getPocketById(pocketId)

                var userId=result[0].userId
                var id=result[0].id
                val nameUpdate=etPocketName.text.toString()
                val balanceUpdate:Double=etPocketBalance.text.toString().toDouble()

                db.pocketDao().updatePocket(pocket(id, userId, nameUpdate, balanceUpdate))
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,pocketFragment()).commit()
            }

        }

        btnDelete=binding.buttonDeletePocketDetailPage
        btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch() {
                var result=db.pocketDao().getPocketById(pocketId)

                db.pocketDao().deletePocket(result[0])
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,pocketFragment()).commit()
            }
        }


    }
}