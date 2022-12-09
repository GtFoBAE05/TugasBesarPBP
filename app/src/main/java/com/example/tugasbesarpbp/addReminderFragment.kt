package com.example.tugasbesarpbp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.ReminderApi
import com.example.tugasbesarpbp.databinding.FragmentAddBillBinding
import com.example.tugasbesarpbp.databinding.FragmentAddReminderBinding
import com.example.tugasbesarpbp.models.Reminder
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_reminder.view.*
import kotlinx.android.synthetic.main.fragment_bill_detail.view.*
import kotlinx.android.synthetic.main.fragment_reminder.view.*
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets
import java.util.*


class addReminderFragment : Fragment() {
    lateinit var tietName: TextInputEditText
    lateinit var tietDate: TextInputEditText
    lateinit var btn: Button
    private var queue : RequestQueue? = null
    var picker: DatePickerDialog? = null

    lateinit var binding: FragmentAddReminderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentAddReminderBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue = Volley.newRequestQueue(requireContext())

        tietName= view.tietAddReminderName
        tietDate= view.tietAddReminderDate

        tietDate.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            picker = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth -> tietDate.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker!!.show()
        }

        btn = view.btnAddReminderButton
        btn.setOnClickListener {
            if(tietName!!.text.toString().isEmpty()){
                MotionToast.createToast(
                    requireActivity(),
                    "ERROR ☹️",
                    "Nama Reminder tidak boleh kosong",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        requireActivity(),
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }else if(tietDate!!.text.toString().isEmpty()){
                MotionToast.createToast(
                    requireActivity(),
                    "ERROR ☹️",
                    "Tanggal Reminder tidak boleh kosong",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        requireActivity(),
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }else{
                addReminder()
            }

        }
    }

    fun addReminder(){
        var userId = requireActivity().intent.getIntExtra("idLogin", 0)

        val reminder= Reminder(userId, tietName.text.toString(), tietDate.text.toString())

        val stringRequest:StringRequest= object : StringRequest(Method.POST, ReminderApi.ADD_URL, Response.Listener { response ->
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, reminderFragment()).commit()

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
                val requestBody= gson.toJson(reminder)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

}