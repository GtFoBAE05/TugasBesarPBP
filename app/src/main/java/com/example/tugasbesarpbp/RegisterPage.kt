package com.example.tugasbesarpbp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.databinding.ActivityHomeBinding.inflate
import com.example.tugasbesarpbp.databinding.ActivityRegisterPageBinding
import com.example.tugasbesarpbp.databinding.FragmentProfileBinding.inflate
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class RegisterPage : AppCompatActivity() {
    val db by lazy { UserDB(this) }

    lateinit var binding: ActivityRegisterPageBinding
    private lateinit var mainLayoutRegisterPage: ConstraintLayout

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText
    private lateinit var tietEmail: TextInputEditText
    private lateinit var tietDate: TextInputEditText
    private lateinit var tietNoTelp: TextInputEditText

    private lateinit var btnRegister: Button
    var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setContentView(R.layout.activity_register_page)

        mainLayoutRegisterPage=binding.mainLayoutRegisterPage

        tietUsername=binding.tietUsernameRegister
        tietPassword=binding.tietPasswordRegister
        tietEmail=binding.tietEmailRegister
        tietDate=binding.tietDateRegister
        tietNoTelp=binding.tietNoTelpRegister

        btnRegister=binding.btnFromRegisterPage

        tietDate.setOnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> tietDate.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker!!.show()
        }

        btnRegister.setOnClickListener {
            val usernameRegister:String= tietUsername.text.toString()
            val passwordRegister:String= tietPassword.text.toString()
            val emailRegister:String= tietEmail.text.toString()
            val dateRegister:String= tietDate.text.toString()
            val noTelpRegister:String = tietNoTelp.text.toString()

            if(usernameRegister.isEmpty()){
                tietUsername.setError("Username must be filled with text!")
            }

            if(passwordRegister.isEmpty()){
                tietPassword.setError("Password must be filled with text!")
            }

            if(emailRegister.isEmpty()){
                tietEmail.setError("Email must be filled with text!")
            }

            if(dateRegister.isEmpty()){
                tietDate.setError("Date must be selected!")
            }

            if(noTelpRegister.isEmpty()){
                tietNoTelp.setError("Phone Number must be filled with text!")
            }

            if(usernameRegister.isNotEmpty() && passwordRegister.isNotEmpty() && emailRegister.isNotEmpty() && dateRegister.isNotEmpty() && noTelpRegister.isNotEmpty()){
                val intent= Intent(this@RegisterPage, LoginPage::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(User(0, usernameRegister, passwordRegister, emailRegister, dateRegister, noTelpRegister ))
                    finish()

                }

//                val bundle = Bundle()
//
//                bundle.putString("username", usernameRegister)
//                bundle.putString("password", passwordRegister)
//                bundle.putString("email", emailRegister)
//                bundle.putString("date", dateRegister)
//                bundle.putString("noTelp", noTelpRegister)
//
//                LoginInfo.listOfLogin.add(LoginInfo(usernameRegister,passwordRegister,emailRegister,dateRegister,noTelpRegister))
//
                intent.putExtra("from","register")
//
//                intent.putExtra("log",bundle)

                startActivity(intent)
            }
        }
    }
}