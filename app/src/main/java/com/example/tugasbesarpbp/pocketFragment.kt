package com.example.tugasbesarpbp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.PocketApi
import com.example.tugasbesarpbp.databinding.FragmentPocketBinding
import com.example.tugasbesarpbp.models.Pocket
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class pocketFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }

    lateinit var binding: FragmentPocketBinding
    lateinit var btn: Button
    private var queue: RequestQueue? = null
    var adapter: rvPocketAdapter?=null

    override fun onStart() {
        super.onStart()
        getAllPocket()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentPocketBinding.inflate(inflater, container, false)
        val view= binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        CoroutineScope(Dispatchers.IO).launch {
//            val idUser= requireActivity().intent.getIntExtra("idLogin",0)
//
//            var result= db.pocketDao().getPocket(idUser)
//
//
//            val adapter: rvPocketAdapter = rvPocketAdapter(result, object : rvPocketAdapter.OnAdapterListener{
//                override fun onClick(pocket: pocket) {
//                    requireActivity().intent.putExtra("pocketId",pocket.id)
//                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketDetailFragment()).commit()
//                }
//            })
//            val layoutManager= GridLayoutManager(requireContext(),2)
//            val rvPocket: RecyclerView = view.findViewById(R.id.rvPocketPage)
//
//            rvPocket.layoutManager=layoutManager
//
//            rvPocket.setHasFixedSize(true)
//
//            rvPocket.adapter=adapter
//
//        }

        queue= Volley.newRequestQueue(requireContext())
        adapter= rvPocketAdapter(ArrayList(), object : rvPocketAdapter.OnAdapterListener{
            override fun onClick(pocket: Pocket) {
                requireActivity().intent.putExtra("pocketId", pocket.id)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketDetailFragment()).commit()
            }

        })
        val layoutManager= GridLayoutManager(requireContext(),2)
        val rvPocket: RecyclerView = view.findViewById(R.id.rvPocketPage)

        rvPocket.layoutManager=layoutManager

        rvPocket.setHasFixedSize(true)

        rvPocket.adapter=adapter



        btn=view.findViewById(R.id.addPocketButton)
        btn.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, addPocketFragment()).commit()
        }

    }

    fun getAllPocket(){
        val stringRequest: StringRequest = object : StringRequest(Method.GET, PocketApi.GET_ALL_URL, Response.Listener {  response ->
            val gson= Gson()
            val pocket: Array<Pocket> = gson.fromJson(response, Array<Pocket>::class.java)

            val id = requireActivity().intent.getIntExtra("idLogin",0)
            adapter!!.setList(id, pocket)

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