package com.example.tugasbesarpbp

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class LoginPage : AppCompatActivity() {
    private lateinit var mainLayout: ConstraintLayout

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText

    private lateinit var btnClear: Button
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login_page)

        mainLayout=findViewById(R.id.mainLayout)
        tietUsername=findViewById(R.id.tietUsernameLogin)
        tietPassword=findViewById(R.id.tietPasswordLogin)
        btnClear=findViewById(R.id.btnClear)
        btnRegister=findViewById(R.id.btnRegister)
        btnLogin=findViewById(R.id.btnLogin)

        btnClear.setOnClickListener {
            tietUsername.setText("")
            tietPassword.setText("")

            Snackbar.make(mainLayout,"Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        btnRegister.setOnClickListener {
            val intent= Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            var checkLogin=false
            val username:String= tietUsername.text.toString()
            val password:String= tietPassword.text.toString()

            if(username.isEmpty()){
                tietUsername.setError("Username must be filled with text!")
                checkLogin=false
            }

            if(password.isEmpty()){
                tietPassword.setError("Password must be filled with text!")
                checkLogin=false
            }

//            if(username=="admin" && password=="admin") checkLogin=true
//
//            if(username!="admin" && password!="admin"){
//                Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
//            }

            for (item in LoginInfo.listOfLogin){
                if(item.username==username)  {
                    if(item.password==password){
                        checkLogin=true
                        break
                    }
                }
                Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
            }

            if(!checkLogin) return@setOnClickListener

            val intent=Intent(this@LoginPage, homeActivity::class.java)
            startActivity(intent)

        }


    }
}