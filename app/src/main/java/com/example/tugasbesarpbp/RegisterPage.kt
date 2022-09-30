package com.example.tugasbesarpbp

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugasbesarpbp.R.drawable
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.databinding.ActivityRegisterPageBinding
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

    private val CHANNEL="channel_notification"
    private val notification=100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}