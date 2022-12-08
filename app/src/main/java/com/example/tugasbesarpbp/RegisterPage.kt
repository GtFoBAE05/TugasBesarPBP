package com.example.tugasbesarpbp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Base64.*
import android.webkit.URLUtil.decode
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.awesomedialog.*
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.api.UsersApi
import com.example.tugasbesarpbp.databinding.ActivityRegisterPageBinding
import com.example.tugasbesarpbp.models.Users
import com.google.android.gms.common.util.Base64Utils.decode
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validNumber
import kotlinx.android.synthetic.main.activity_register_page.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


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

    private val CHANNEL="channel_notification"
    private val notification=100

    private var queue : RequestQueue? = null
    lateinit var ivValue: ByteArray

    val algorithm = "AES/CBC/PKCS5Padding"
    val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
    val iv = IvParameterSpec(ByteArray(16))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding= ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainLayoutRegisterPage=binding.mainLayoutRegisterPage

        queue = Volley.newRequestQueue(this)

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

//            if(usernameRegister.isEmpty()){
//                tietUsername.setError("Username must be filled with text!")
//            }

            usernameRegister.nonEmpty(){msg ->
                tietUsername.setError(msg)
            }

            passwordRegister.nonEmpty(){msg ->
                tietPassword.setError(msg)
            }

            emailRegister.validEmail(){msg ->
                tietEmail.setError(msg)
            }

            dateRegister.nonEmpty(){msg ->
                tietDate.setError(msg)
            }

            noTelpRegister.validNumber(){msg ->
                tietNoTelp.setError(msg)
            }

            if(usernameRegister.nonEmpty() && passwordRegister.nonEmpty() && emailRegister.validEmail() && dateRegister.nonEmpty() && noTelpRegister.validNumber()){

                val intent= Intent(this@RegisterPage, LoginPage::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(User(0, usernameRegister, passwordRegister, emailRegister, dateRegister, noTelpRegister ))
                    finish()

                }

                createUser()

                createNotificationChannel()
                sendNotification()

           intent.putExtra("from","register")
                    startActivity(intent)


//                val bundle = Bundle()
//
//                bundle.putString("username", usernameRegister)
//                bundle.putString("passw+ord", passwordRegister)
//                bundle.putString("email", emailRegister)
//                bundle.putString("date", dateRegister)
//                bundle.putString("noTelp", noTelpRegister)
//
//                LoginInfo.listOfLogin.add(LoginInfo(usernameRegister,passwordRegister,emailRegister,dateRegister,noTelpRegister))
//
//
//                intent.putExtra("log",bundle)

            }
        }
    }

    private fun createUser(){
        val usernameRegister:String= tietUsername.text.toString()
        val passwordRegister:String= tietPassword.text.toString()
        val emailRegister:String= tietEmail.text.toString()
        val dateRegister:String= tietDate.text.toString()
        val noTelpRegister:String = tietNoTelp.text.toString()



        var user= Users(usernameRegister,encrypt(algorithm, passwordRegister,key,iv), emailRegister, dateRegister, noTelpRegister)

        val stringRequest: StringRequest = object : StringRequest(Method.POST, UsersApi.ADD_URL, Response.Listener { response ->
            val gson = Gson()
            val user = gson.fromJson(response, Users::class.java)

        }, Response.ErrorListener { error ->
            try {
                val responseBody= String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)
                println(errors.getString("message"))
            } catch (e:Exception){
                println(e.message)
            }
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers= HashMap<String, String>()
                headers["Accept"]= "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson= Gson()
                val requestBody= gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        queue!!.add(stringRequest)
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name="Notification Title"
            val descriptionText="Notification Description"

            val channel=NotificationChannel(CHANNEL,name,NotificationManager.IMPORTANCE_DEFAULT).apply {
                description=descriptionText
            }

            val notificationManager: NotificationManager=
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val bpStyle= NotificationCompat.BigPictureStyle();
        bpStyle.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.success_register))

        val resultIntent:Intent = Intent(this, LoginPage::class.java)

        resultIntent.putExtra("btnFillClicked", "true")

        val pendingIntent= PendingIntent.getActivity(this,0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this,CHANNEL)
            .setSmallIcon(R.drawable.ic_baseline_how_to_reg_24)
            .setContentTitle("SUCCESS REGISTER")
            .setContentText("Tekan Fill pada notification untuk auto-fill login page")
            .setStyle(bpStyle)
            .setColor(Color.RED)

            .addAction(R.drawable.ic_baseline_login_24,"fill", pendingIntent)


        with(NotificationManagerCompat.from(this)){
            notify(notification,builder.build())
        }

    }

    fun encrypt(algorithm: String, inputText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(inputText.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }


}