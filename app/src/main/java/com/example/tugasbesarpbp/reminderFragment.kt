package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.adapter.ReminderAdapter
import com.example.tugasbesarpbp.api.ReminderApi
import com.example.tugasbesarpbp.databinding.FragmentReminderBinding
import com.example.tugasbesarpbp.models.Reminder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_reminder.view.*
import kotlinx.android.synthetic.main.rv_reminder.view.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class reminderFragment : Fragment() {
    lateinit var binding: FragmentReminderBinding

    lateinit var adapter: ReminderAdapter

    lateinit var btn: Button

    private var queue: RequestQueue? = null

    override fun onStart() {
        super.onStart()
        getReminder()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentReminderBinding.inflate(inflater,container,false)
        val view= binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue= Volley.newRequestQueue(requireContext())


        adapter = ReminderAdapter(ArrayList(), object: ReminderAdapter.OnAdapterListener{
            override fun onClick(reminder: Reminder) {
                requireActivity().intent.putExtra("reminderId", reminder.id)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, reminderDetailFragment()).commit()
            }
        })

        val layoutManager= LinearLayoutManager(requireContext())
        val rvReminder= view.rvReminder
        rvReminder.adapter= adapter
        rvReminder.layoutManager= layoutManager

        btn= view.btnAddReminder
        btn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, addReminderFragment()).commit()
        }

    }

    fun getReminder(){
        val stringRequest: StringRequest = object : StringRequest(Method.GET, ReminderApi.GET_ALL_URL, Response.Listener { response ->
            val gson = Gson()
            val reminder = gson.fromJson(response, Array<Reminder>::class.java)

            val id = requireActivity().intent.getIntExtra("idLogin",0)
            adapter.setList(id, reminder)

        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)

            }catch (e:Exception){

            }
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