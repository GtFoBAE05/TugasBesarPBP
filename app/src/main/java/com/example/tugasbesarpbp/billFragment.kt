package com.example.tugasbesarpbp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.adapter.BillAdapter
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.databinding.FragmentBillBinding
import com.example.tugasbesarpbp.models.Bill
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_bill.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class billFragment : Fragment() {


    private var billAdapter: BillAdapter? = null
    lateinit var btnAdd: Button
    private var queue: RequestQueue? = null
    private var res: List<Bill>? = null
    private var svBil: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding= FragmentBillBinding.inflate(inflater, container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue= Volley.newRequestQueue(requireContext())

        val rvBill = view.rv_bill


        billAdapter= BillAdapter(ArrayList(), requireContext(), object : BillAdapter.OnAdapterListener{
            override fun onClick(bill: Bill) {
                requireActivity().intent.putExtra("billId", bill.id)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, billDetailFragment()).commit()
            }
        })

        rvBill.layoutManager = LinearLayoutManager(requireContext())
        rvBill.adapter= billAdapter

        btnAdd = view.AddBillButton
        btnAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, addBillFragment()).commit()
        }

        svBil= view.svBill
        svBil?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                billAdapter!!.filter.filter(p0)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        getAllBill()


    }

//    override fun onStart() {
//        super.onStart()
//        getAllBill()
//    }

    fun getAllBill(){
        val stringRequest:StringRequest = object : StringRequest(Method.GET, BillApi.GET_ALL_URL, Response.Listener { response ->
            val gson= Gson()
            val bill: Array<Bill> = gson.fromJson(response, Array<Bill>::class.java)

            var strResp = bill.toString()

            val res = gson.toJson(bill)

//            val jsonObj: JSONObject = JSONObject(strResp)
//            val jsonArray: JSONArray = jsonObj.getJSONArray("data")
//            println(res.toString())
//            println(bill::class.simpleName)
//            var bill: Array<Bill> = gson.fromJson(strResp, Array<Bill>::class.java)
//            val gson = GsonBuilder().setPrettyPrinting().create()
//            val result = gson.fromJson(response,)



            val id = requireActivity().intent.getIntExtra("idLogin",0)

            billAdapter!!.setId(id)

            billAdapter!!.setBillList(bill)

            billAdapter!!.filter.filter(svBil!!.query)




        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)

            }catch (e:Exception){}

            })

        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "Application/json"
                return headers
            }
        }

        queue!!.add(stringRequest)
    }


}