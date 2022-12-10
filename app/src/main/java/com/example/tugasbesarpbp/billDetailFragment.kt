package com.example.tugasbesarpbp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.models.Bill
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.musfickjamil.snackify.Snackify
import kotlinx.android.synthetic.main.fragment_bill_detail.view.*
import kotlinx.android.synthetic.main.rv_bill.view.*
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

class billDetailFragment : Fragment() {
    private var queue: RequestQueue? = null
    lateinit var tietBillName:TextInputEditText
    lateinit var tietBillDate:TextInputEditText
    lateinit var tietBillAmount:EditText

    var picker: DatePickerDialog? = null


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

        tietBillDate.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            picker = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth -> tietBillDate.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker!!.show()
        }


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
            val bill= gson.fromJson(response, Bill::class.java)


            tietBillName!!.setText(bill.name)
            tietBillDate!!.setText(bill.date)
            tietBillAmount!!.setText(bill.amount.toString())



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

        if(tietBillName.text.toString().isEmpty()){
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
        }else if(tietBillDate.text.toString().isEmpty()){
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
        }else if(tietBillAmount.text.toString().isEmpty()){
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