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
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*


class updateProfileFragment : Fragment() {

    val db by lazy { UserDB(requireContext()) }


    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText
    private lateinit var tietEmail: TextInputEditText
    private lateinit var tietDate: TextInputEditText
    private lateinit var tietNoTelp: TextInputEditText

    private lateinit var otUsernameUpdade: TextInputLayout

    private lateinit var btnUpdateAccount: Button
    var picker: DatePickerDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tietUsername=view.findViewById(R.id.tietUsernameUpdate)
        tietPassword=view.findViewById(R.id.tietPasswordUpdate)
        tietEmail=view.findViewById(R.id.tietEmailUpdate)
        tietDate=view.findViewById(R.id.tietDateUpdate)
        tietNoTelp=view.findViewById(R.id.tietNoTelpUpdate)

        otUsernameUpdade=view.findViewById(R.id.outlinedTextFieldUsernameUpdate)

        btnUpdateAccount=view.findViewById(R.id.btnFromUpdatePage)

        val userId= requireActivity().intent.getIntExtra("idLogin",0)
//        CoroutineScope(Dispatchers.IO).launch {
//            var result= db.userDao().getUserById(userId)
//            println(result)
//            tietUsername.setText(result[0].username)
//
//        }


//        tietPassword.setText((result[0].password))
//        tietEmail.setText(result[0].email)
//        tietNoTelp.setText(result[0].NoTelp)

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
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().updateUser(User(userId,usernameUpdate, passwordUpdate, emailUpdate, dateUpdate, noTelpUpdate))


                }
                requireActivity().intent.putExtra("usernameLogin",usernameUpdate)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,profileFragment()).commit()
            }
        }

    }


}