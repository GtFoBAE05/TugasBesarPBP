package com.example.tugasbesarpbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.example.tugasbesarpbp.Room.Constant
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.api.UsersApi
import com.example.tugasbesarpbp.databinding.ActivityLoginPageBinding
import com.example.tugasbesarpbp.entity.LoginInfo
import com.example.tugasbesarpbp.models.Users
import com.github.venom.Venom
import com.github.venom.service.NotificationConfig
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class LoginPage : AppCompatActivity() {
    val db by lazy { UserDB(this) }

    private var queue: RequestQueue? = null

    private lateinit var mainLayout: ConstraintLayout

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText

    private lateinit var btnClear: Button
    private lateinit var btnLogin: Button
    private lateinit var btnToRegisterPage: Button

    private val myPreference="myPref"
    var sharedPreferences: SharedPreferences?=null

    private val namePref="nameKey"
    private val passPref="passKey"

    private val CHANNEL_ID_1 = "channel_notification01"
    private val notificationId1 = 101

    private var resultUser : Users?= null
    private var id:Int?=null
    private var resss : Array<Users>?=null



    override fun onStart() {
        super.onStart()
        getUser()
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login_page)

        val venom = Venom.createInstance(this)


        val notification = NotificationConfig.Builder(this)
            .buttonCancel(com.github.venom.R.string.venom_notification_button_cancel)
            .buttonKill(getString(com.github.venom.R.string.venom_notification_button_kill))
            .build()

        venom.initialize(notification)
        venom.start()

        if(intent.getStringExtra("from").equals("register")){
            AwesomeDialog.build(this)
                .title("Congratulations")
                .body("Your New Account has been created")
                .onPositive("Okay") {
                }
        }


        val binding: ActivityLoginPageBinding= DataBindingUtil.setContentView(this, R.layout.activity_login_page)

        createNotificationChannel()

        queue= Volley.newRequestQueue(this)

        mainLayout=binding.mainLayout

        tietUsername=binding.tietUsernameLogin
        tietPassword=binding.tietPasswordLogin


        btnClear=binding.btnClear
        btnToRegisterPage=binding.btnToRegister
        btnLogin=binding.btnLogin

        sharedPreferences= getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        if (sharedPreferences!!.contains(namePref)) {
            tietUsername?.setText(sharedPreferences!!.getString(namePref, ""))
        }
        if (sharedPreferences!!.contains(passPref)) {
            tietPassword?.setText(sharedPreferences!!.getString(passPref, ""))
        }



        var fillBtnMsg=intent.getStringExtra("btnFillClicked")
        println("fill Btn msg= "+fillBtnMsg)

        if(fillBtnMsg.equals("true")){

            CoroutineScope(Dispatchers.IO).launch {
                var getLastUser: List<User> = db.userDao().getUsers()

                println("username - pass" + getLastUser.last().username  + " - "+ getLastUser.last().password)


                runOnUiThread(){
                    tietUsername.setText("")
                    tietPassword.setText("")

                    tietUsername.setText(getLastUser.last().username)
                    tietPassword.setText(getLastUser.last().password)

                    Snackbar.make(mainLayout,"success fill login form", Snackbar.LENGTH_LONG).show()
                }


            }
        }



//        val lastActivity= intent.getStringExtra("from")

//        if(lastActivity.equals("register")){
////            Snackbar.make(mainLayout,"Success create account", Snackbar.LENGTH_LONG).show()
////            val bundle: Bundle=intent.getBundleExtra("log")!!
////            tietUsername.setText(bundle.getString("username"))
////            tietPassword.setText(bundle.getString("password"))
//            var userId = intent.getIntExtra("intent_id",0)
//
//            CoroutineScope(Dispatchers.IO).launch {
//                val user= db.userDao().getUser(0)[0]
//                tietUsername.setText(user.username)
//                tietPassword.setText(user.password)
//            }
//        }

        btnClear.setOnClickListener {
            tietUsername.setText("")
            tietPassword.setText("")

            Snackbar.make(mainLayout,"Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        btnToRegisterPage.setOnClickListener {
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

//            for (element in resultUser){
//                if(element.username.equals(username) && element.password.equals(password)){
//                    val intent=Intent(this@LoginPage, homeActivity::class.java)
//                    intent.putExtra("usernameLogin",username)
//                    intent.putExtra("idLogin", element.id)
//
//                    //save to sharedPreference
//                    val editor: SharedPreferences.Editor= sharedPreferences!!.edit()
//                    editor.putString(namePref,username)
//                    editor.putString(passPref,password)
//                    editor.apply()
//
//                    startActivity(intent)
//                }else{
//                    Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
//                }
//            }

            for (element in resss!!){
                if(element.username.equals(username) && element.password.equals(password)){
                    checkLogin=true
                    val intent=Intent(this@LoginPage, homeActivity::class.java)
                    intent.putExtra("usernameLogin",username)
                    intent.putExtra("idLogin", element.id)

                    //save to sharedPreference
                    val editor: SharedPreferences.Editor= sharedPreferences!!.edit()
                    editor.putString(namePref,username)
                    editor.putString(passPref,password)
                    editor.apply()

                    startActivity(intent)

                }else{
                    Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
                }
            }

//            for (item in LoginInfo.listOfLogin){
//                if(item.username.equals(username))  {
//                    if(item.password.equals(password)){
//                        checkLogin=true
//                        break
//                    }
//                }
//                Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
//            }


//            CoroutineScope(Dispatchers.IO).launch {
//
//                //ini jalan
//                var resultCheckUser: List<User> = db.userDao().checkUser(username,password)
//                println("hasil: " + resultCheckUser)
//
//                if(resultCheckUser.isNullOrEmpty()){
//                    Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
//                    return@launch
//                }
//
//                if(resultCheckUser[0].username.equals(username) && resultCheckUser[0].password.equals(password)){
//                    checkLogin=true
//                    val intent=Intent(this@LoginPage, homeActivity::class.java)
//                    intent.putExtra("usernameLogin",username)
//                    intent.putExtra("idLogin",resultCheckUser[0].id)
//
//                    println(resultCheckUser[0].id)
//
//                    //save to sharedPreference
//                    val editor: SharedPreferences.Editor= sharedPreferences!!.edit()
//                    editor.putString(namePref,username)
//                    editor.putString(passPref,password)
//                    editor.apply()
//
//                    startActivity(intent)
//                }
//            }

            //Snackbar.make(mainLayout,"User not found", Snackbar.LENGTH_LONG).show()
            //if(!checkLogin) return@setOnClickListener

//            val intent=Intent(this@LoginPage, homeActivity::class.java)
//            intent.putExtra("usernameLogin",username)
//            startActivity(intent)
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1= NotificationChannel(CHANNEL_ID_1,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description=descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun getUser(){

        val stringRequest: StringRequest = object : StringRequest(Method.GET, UsersApi.GET_ALL_URL, Response.Listener { response ->
            val gson= Gson()
            val users : Array<Users> = gson.fromJson(response, Array<Users>::class.java)

            resss=users

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
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "Application/json"
                return headers
            }
        }

        queue!!.add(stringRequest)


    }



}