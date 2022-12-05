package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.models.Bill
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_bill.view.*
import kotlinx.android.synthetic.main.fragment_bill_detail.view.*
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets


class addBillFragment : Fragment() {
    private var tietBillName : TextInputEditText? = null
    private var tietBillDate: TextInputEditText?=null
    private var tietBillAmount: TextInputEditText?=null
    private var btnAdd : Button? = null
    private var queue : RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue = Volley.newRequestQueue(requireContext())

        tietBillName = view.tietAddBillName
        tietBillDate = view.tietAddBillDate
        tietBillAmount = view.tietAddBillAmount


        var btnAdd = view.btnAddBillButton
        btnAdd.setOnClickListener {
            addBill()
        }

    }

    private fun addBill(){
        var userId = requireActivity().intent.getIntExtra("idLogin", 0)

        if(tietBillName!!.text.toString().isEmpty()){
            MotionToast.createToast(
                requireActivity(),
                "ERROR ☹️",
                "Nama Bill tidak boleh kosong",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    requireActivity(),
                    www.sanju.motiontoast.R.font.helvetica_regular
                )
            )
        }else if(tietBillDate!!.text.toString().isEmpty()){
            MotionToast.createToast(
                requireActivity(),
                "ERROR ☹️",
                "Tanggal Bill tidak boleh kosong",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    requireActivity(),
                    www.sanju.motiontoast.R.font.helvetica_regular
                )
            )
        }else if(tietBillAmount!!.text.toString().isEmpty()){
            MotionToast.createToast(
                requireActivity(),
                "ERROR ☹️",
                "Nominal Bill tidak boleh kosong",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(
                    requireActivity(),
                    www.sanju.motiontoast.R.font.helvetica_regular
                )
            )
        }else{
            val bill = Bill(
                userId, tietBillName!!.text.toString(),tietBillDate!!.text.toString(),tietBillAmount!!.text.toString().toDouble()
            )

            val stringRequest: StringRequest = object : StringRequest(Method.POST, BillApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var bill = gson.fromJson(response, Bill::class.java)
                val res = gson.toJson(bill)

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, billFragment()).commit()

            },Response.ErrorListener { error ->
                try {
                    val responseBody= String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors= JSONObject(responseBody)
                    println(errors.getString("message"))
                } catch (e:Exception){
                    println(e.message)
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers= HashMap<String, String>()
                    headers["Accept"]= "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson= Gson()
                    val requestBody= gson.toJson(bill)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
            queue!!.add(stringRequest)
        }
    }

}