package com.example.tugasbesarpbp

import android.content.Intent
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
import com.example.tugasbesarpbp.databinding.FragmentPocketBinding
import com.example.tugasbesarpbp.databinding.FragmentPocketDetailBinding
import com.example.tugasbesarpbp.models.Pocket
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets

class pocketDetailFragment : Fragment() {

    val db by lazy { PocketDB(requireContext()) }

    lateinit var binding: FragmentPocketDetailBinding

    private lateinit var etPocketName:EditText
    private lateinit var etPocketBalance:EditText

    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button

    private var queue: RequestQueue? = null

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

        queue = Volley.newRequestQueue(requireContext())

        etPocketName=binding.editPocketName
        etPocketBalance=binding.editPocketBalance

        var name:String
        var balance:String
        var pocketId= requireActivity().intent.getIntExtra("pocketId",0)

        var result: List<pocket>

        getPocketById(pocketId)
//        CoroutineScope(Dispatchers.IO).launch {
//
//            result=db.pocketDao().getPocketById(pocketId)
//
//            name=result[0].pocketName.toString()
//            balance=result[0].pocketBalance.toString()
//
//            println("test:" + name +"  "+ balance)
//
//            requireActivity().runOnUiThread() {
//                etPocketName.setText(name)
//                etPocketBalance.setText(balance)
//            }
//
//        }

        btnUpdate=binding.buttonUpdatePocketDetailPage
        btnUpdate.setOnClickListener {

            if(etPocketName.text.isEmpty()){
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
            }else if(etPocketBalance.text.toString().isEmpty()){
                MotionToast.createToast(requireActivity(),
                    "ERROR ☹️",
                    "Nominal tidak boleh kosong",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(requireActivity(), www.sanju.motiontoast.R.font.helvetica_regular))
            }else{

                updatePocket(pocketId)

//                CoroutineScope(Dispatchers.IO).launch {
//                    var pocketId= requireActivity().intent.getIntExtra("pocketId",0)
//                    var result=db.pocketDao().getPocketById(pocketId)
//
//                    var userId=result[0].userId
//                    var id=result[0].id
//                    val nameUpdate=etPocketName.text.toString()
//                    val balanceUpdate:Double=etPocketBalance.text.toString().toDouble()
//
//                    db.pocketDao().updatePocket(pocket(id, userId, nameUpdate, balanceUpdate))
//                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,pocketFragment()).commit()
//                }
            }
        }

        btnDelete=binding.buttonDeletePocketDetailPage
        btnDelete.setOnClickListener {
            deletePocket(pocketId)
//            CoroutineScope(Dispatchers.IO).launch() {
//                var result=db.pocketDao().getPocketById(pocketId)
//
//                db.pocketDao().deletePocket(result[0])
//                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,pocketFragment()).commit()
//            }
        }
    }

    fun getPocketById(id : Int){

        val stringRequest: StringRequest = object : StringRequest(Method.GET, PocketApi.GET_BY_ID_URL+id, Response.Listener{ response ->

            val gson = Gson()
            val pocket = gson.fromJson(response, Pocket::class.java)

            etPocketName.setText(pocket.name)
            etPocketBalance.setText(pocket.balance.toString())

        }, Response.ErrorListener {error ->
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
                val headers = HashMap<String, String>()
                headers["Accept"] = "Application/json"
                return headers
            }
        }

        queue!!.add(stringRequest)
    }

    fun updatePocket(id: Int){
        val pocket = Pocket(id, etPocketName.text.toString(), etPocketBalance.text.toString().toDouble())
        val stringRequest = object : StringRequest(Method.PUT, PocketApi.UPDATE_URL + id, Response.Listener { response ->
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
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(pocket)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        queue!!.add(stringRequest)
    }

    fun deletePocket(id:Int){
        val stringRequest = object : StringRequest(Method.DELETE, PocketApi.DELETE_URL+id, Response.Listener { response ->
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
                val headers = HashMap<String,String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

        queue!!.add(stringRequest)
    }
}