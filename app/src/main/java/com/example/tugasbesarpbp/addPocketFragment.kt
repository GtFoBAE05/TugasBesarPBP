package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.example.tugasbesarpbp.databinding.FragmentAddPocketBinding
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import com.wajahatkarim3.easyvalidation.core.collection_ktx.validNumberList
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


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

            if(pocketName.text.isEmpty()) {
                MotionToast.createToast(
                    requireActivity(),
                    "ERROR ☹️",
                    "Nama pocket tidak boleh kosong",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        requireActivity(),
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }else if(pocketBalance.text.isEmpty()){
                MotionToast.createToast(requireActivity(),
                    "ERROR ☹️",
                    "Nominal tidak boleh kosong",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireActivity(), www.sanju.motiontoast.R.font.helvetica_regular))
            }else{

                val name=pocketName.text.toString()
                val balance : Double= pocketBalance.text.toString().toDouble()

                CoroutineScope(Dispatchers.IO).launch {
                    db.pocketDao().addPocket(pocket(0,idUser, name,balance))
                }
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketFragment()).commit()

            }







//            if(name.isNotEmpty() && balance!=null){
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.pocketDao().addPocket(pocket(0,idUser, name,balance))
//                }
//            }
//
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketFragment()).commit()
        }
    }
}