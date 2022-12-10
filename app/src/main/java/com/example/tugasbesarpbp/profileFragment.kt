package com.example.tugasbesarpbp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.tugasbesarpbp.databinding.ActivityHomeBinding
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import android.media.Image
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.api.UsersApi
import com.example.tugasbesarpbp.databinding.FragmentProfileBinding
import com.example.tugasbesarpbp.models.Bill
import com.example.tugasbesarpbp.models.Users
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import org.intellij.lang.annotations.JdkConstants

class profileFragment : Fragment() {

    val db by lazy { UserDB(requireContext()) }

    lateinit var btnLogout:Button
    lateinit var btnUpdate:Button
    lateinit var tvUsername:TextView
    lateinit var tvEmail:TextView
    lateinit var tvDate:TextView
    lateinit var tvPhone:TextView
    lateinit var btnMap: Button

    lateinit var binding: FragmentProfileBinding

    private var queue: RequestQueue? = null

    override fun onStart() {
        super.onStart()
        val userId= requireActivity().intent.getIntExtra("idLogin",0)
        getUsersById(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue= Volley.newRequestQueue(requireContext())

        tvUsername=binding.txtUsernameProfile
        tvEmail=binding.txtEmailProfile
        tvDate=binding.txtDateProfile
        tvPhone=binding.txtPhoneNumber

        val userId= requireActivity().intent.getIntExtra("idLogin",0)

//        CoroutineScope(Dispatchers.IO).launch{
//            println("user id=" + userId)
//            val resultCheckUser: List<User> = db.userDao().getUserById(userId)
//            println("hasil=" + resultCheckUser)
//            tvUsername.setText("Username:" +resultCheckUser[0].username)
//            tvEmail.setText("Email:" +resultCheckUser[0].email)
//            tvDate.setText("Birth:" +resultCheckUser[0].date)
//            tvPhone.setText("Phone number:" +resultCheckUser[0].NoTelp)
//        }

        //getUsersById(userId)


        btnUpdate=binding.btnUpdateProfile
        btnUpdate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, updateProfileFragment()).commit()
        }

        btnLogout=binding.btnLogoutInProfilePage
        btnLogout.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Are you sure want to exit?").setPositiveButton("YES", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    activity!!.finishAndRemoveTask()
                }
            }).show()
        }

        btnMap= binding.buttonMap
        btnMap.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, mapFragment()).commit()
        }

        val userPic = view.userProfilePic
        userPic.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, cameraFragment()).commit()
        }
    }

    private fun getUsersById(id: Int){
        val stringRequest: StringRequest = object : StringRequest(Method.GET, UsersApi.GET_BY_ID_URL + id, Response.Listener { response ->
            val gson = Gson()
            val users = gson.fromJson(response, Users::class.java)

            tvUsername.setText("Username:" +users.username)
            tvEmail.setText("Email:" +users.email)
            tvDate.setText("Birth:" +users.date)
            tvPhone.setText("Phone number:" +users.noTelp)




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
}