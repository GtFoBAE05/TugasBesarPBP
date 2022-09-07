package com.example.tugasbesarpbp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginPage : AppCompatActivity() {
    private lateinit var txtRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        txtRegister = findViewById(R.id.txtRegister)

        txtRegister.setOnClickListener {
            val intent= Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }
}