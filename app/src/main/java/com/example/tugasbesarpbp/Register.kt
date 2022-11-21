package com.example.tugasbesarpbp

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var binding :
            ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            saveData()
        }
    }
    fun saveData(){
        with(binding) {
            val username = txtUsername.text.toString()
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            RClient.instances.createData(username,email,password).enqueue (object :
                Callback<ResponseCreate> {
                override fun onResponse(call: Call<ResponseCreate>, response: Response<ResponseCreate>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext,"${response.body()?.pesan}", Toast.LENGTH_LONG).show()
                        finish()
                    }else {
                        val jsonObj =
                            JSONObject(response.errorBody()!!.charStream().readText())

                        txtUsername.setError(jsonObj.getString("message"))
                        Toast.makeText(applicationContext,"Maaf sudah ada datanya", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<ResponseCreate>, t: Throwable) {
                }
            })
        }
    }
}