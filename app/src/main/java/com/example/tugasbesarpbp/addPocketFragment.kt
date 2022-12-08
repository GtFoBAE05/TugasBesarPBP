package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.PocketApi
import com.example.tugasbesarpbp.databinding.FragmentAddPocketBinding
import com.example.tugasbesarpbp.models.Pocket
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import com.google.gson.Gson
import com.wajahatkarim3.easyvalidation.core.collection_ktx.validNumberList
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets


class addPocketFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }

    lateinit var binding: FragmentAddPocketBinding

    lateinit var pocketName: EditText
    lateinit var pocketBalance: EditText

    lateinit var btnAddPocket: Button

    private var queue: RequestQueue? = null

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
        queue = Volley.newRequestQueue(requireContext())
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
                addPocket()


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

    fun addPocket(){
        var userId = requireActivity().intent.getIntExtra("idLogin", 0)

        val pocket= Pocket(userId, pocketName.text.toString(), pocketBalance.text.toString().toDouble() )

        val stringRequest: StringRequest = object : StringRequest(Method.POST, PocketApi.ADD_URL, Response.Listener { response ->
            val gson = Gson()
            var pocket = gson.fromJson(response, Pocket::class.java)

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketFragment()).commit()
        }, Response.ErrorListener { error ->
            try {
                val responseBody= String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)
                println(errors.getString("message"))
            } catch (e:Exception){
                println(e.message)
            }
        })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers= HashMap<String, String>()
                headers["Accept"]= "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson= Gson()
                val requestBody= gson.toJson(pocket)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
}