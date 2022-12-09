package com.example.tugasbesarpbp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.api.UsersApi
import com.example.tugasbesarpbp.databinding.FragmentUpdateProfileBinding
import com.example.tugasbesarpbp.models.Bill
import com.example.tugasbesarpbp.models.Users
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*


class updateProfileFragment : Fragment() {
    private var queue: RequestQueue? = null

    val db by lazy { UserDB(requireContext()) }

    lateinit var binding: FragmentUpdateProfileBinding

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText
    private lateinit var tietEmail: TextInputEditText
    private lateinit var tietDate: TextInputEditText
    private lateinit var tietNoTelp: TextInputEditText

    private lateinit var btnUpdateAccount: Button
    var picker: DatePickerDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpdateProfileBinding.inflate(inflater, container, false)
        val view= binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue= Volley.newRequestQueue(requireContext())

        tietUsername=binding.tietUsernameUpdate
        tietPassword=binding.tietPasswordUpdate
        tietEmail=binding.tietEmailUpdate
        tietDate=binding.tietDateUpdate
        tietNoTelp=binding.tietNoTelpUpdate

        btnUpdateAccount=binding.btnFromUpdatePage

        val userId= requireActivity().intent.getIntExtra("idLogin",0)
        getUsersById(userId)


//        CoroutineScope(Dispatchers.IO).launch {
//            val userId= requireActivity().intent.getIntExtra("idLogin",0)
//            var result= db.userDao().getUserById(userId)
//            println(result)
//
//            requireActivity().runOnUiThread(){
//                tietUsername.setText(result[0].username)
//                tietPassword.setText((result[0].password))
//                tietDate.setText(result[0].date)
//                tietEmail.setText(result[0].email)
//                tietNoTelp.setText(result[0].NoTelp)
//            }
//        }

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

        btnUpdateAccount.setOnClickListener {
            val usernameUpdate:String= tietUsername.text.toString()
            val passwordUpdate:String= tietPassword.text.toString()
            val emailUpdate:String= tietEmail.text.toString()
            val dateUpdate:String= tietDate.text.toString()
            val noTelpUpdate:String = tietNoTelp.text.toString()

            if(usernameUpdate.isEmpty()){
                tietUsername.setError("Username must be filled with text!")
            }

            if(passwordUpdate.isEmpty()){
                tietPassword.setError("Password must be filled with text!")
            }

            if(emailUpdate.isEmpty()){
                tietEmail.setError("Email must be filled with text!")
            }

            if(dateUpdate.isEmpty()){
                tietDate.setError("Date must be selected!")
            }

            if(noTelpUpdate.isEmpty()){
                tietNoTelp.setError("Phone Number must be filled with text!")
            }

            val userId= requireActivity().intent.getIntExtra("idLogin",0)

            if(usernameUpdate.isNotEmpty() && passwordUpdate.isNotEmpty() && emailUpdate.isNotEmpty() && dateUpdate.isNotEmpty() && noTelpUpdate.isNotEmpty()){

//                CoroutineScope(Dispatchers.IO).launch {
//                    db.userDao().updateUser(User(userId,usernameUpdate, passwordUpdate, emailUpdate, dateUpdate, noTelpUpdate))
//                }
                updateUser(userId)

                requireActivity().intent.putExtra("usernameLogin",usernameUpdate)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,profileFragment()).commit()
            }
        }

    }

    private fun getUsersById(id: Int){
        val stringRequest: StringRequest = object : StringRequest(Method.GET, UsersApi.GET_BY_ID_URL + id, Response.Listener { response ->
            val gson = Gson()
            val users= gson.fromJson(response, Users::class.java)

            tietUsername.setText(users.username)
            //tietPassword.setText(users.password)
            tietEmail.setText(users.email)
            tietDate.setText(users.date)
            tietNoTelp.setText(users.noTelp)

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

    private fun updateUser(id: Int){
        val user = Users(
            tietUsername.text.toString(),tietPassword.text.toString(), tietEmail.text.toString(), tietDate.text.toString(), tietNoTelp.text.toString()
        )

        val stringRequest: StringRequest= object : StringRequest(Method.PUT, UsersApi.UPDATE_URL + id, Response.Listener { response ->



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
                val requestBody = gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        queue!!.add(stringRequest)

    }


}