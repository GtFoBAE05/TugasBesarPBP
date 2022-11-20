package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.models.Bill
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bill_detail.view.*
import kotlinx.android.synthetic.main.rv_bill.view.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class billDetailFragment : Fragment() {
    private var queue: RequestQueue? = null
    lateinit var tietBillName:TextInputEditText
    lateinit var tietBillDate:TextInputEditText
    lateinit var tietBillAmount:TextInputEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id = requireActivity().intent.getIntExtra("billId",0)
        var userId = requireActivity().intent.getIntExtra("idLogin", 0)
        //println("billId        " + id)

        queue= Volley.newRequestQueue(requireContext())


        tietBillName=view.tietDetailBillName
        tietBillDate =view.tietDetailBillDate
        tietBillAmount = view.tietDetailBillAmount


        val btnUpdate: Button= view.btnUpdateBill
        btnUpdate.setOnClickListener {
            updateBill(id, userId)
        }

        val btnDelete: Button= view.btnDeleteBill
        btnDelete.setOnClickListener {
            deleteBill(id)
        }

        getBillById(id)

    }

    private fun getBillById(id: Int){
        val stringRequest: StringRequest= object : StringRequest(Method.GET, BillApi.GET_BY_ID_URL + id, Response.Listener { response ->
            val gson = Gson()
            val bill: Array<Bill> = gson.fromJson(response, Array<Bill>::class.java)


            tietBillName!!.setText(bill[0].name)
            tietBillDate!!.setText(bill[0].date)
            tietBillAmount!!.setText(bill[0].amount.toString())



        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)

            }catch (e:Exception){}

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

    private fun updateBill(id: Int, userId: Int){
        val bill = Bill(
            userId, tietBillName.text.toString(),tietBillDate.text.toString(),tietBillAmount.text.toString().toDouble()
        )

        val stringRequest: StringRequest= object : StringRequest(Method.PUT, BillApi.UPDATE_URL + id, Response.Listener { response ->
            val gson = Gson()
            var bill = gson.fromJson(response, Bill::class.java)

            if(bill!=null){
                println("berhasil update")
            }

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, billFragment()).commit()


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
                val requestBody = gson.toJson(bill)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        queue!!.add(stringRequest)

    }

    private fun deleteBill(id: Int){
        val stringRequest: StringRequest = object: StringRequest(Method.DELETE, BillApi.DELETE_URL + id, Response.Listener { response ->


            val gson= Gson()
            var bill = gson.fromJson(response, Bill :: class.java)

            if(bill!=null)
                println("berhasil hapus")

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, billFragment()).commit()
        },Response.ErrorListener { error ->
            try {
            }catch (e: Exception){

            }
        }) {
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