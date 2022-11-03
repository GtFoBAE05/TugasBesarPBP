package com.example.tugasbesarpbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        var sharedPreferences= getSharedPreferences("preferences", MODE_PRIVATE)

        val firstTimeRun= sharedPreferences.getString("firstTimeRun","")

        if(firstTimeRun.equals("false")){
            println("masuk pertama")

            Handler().removeCallbacks({ this })
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)


        }else{
            println("masuk kedua")
            val editor: SharedPreferences.Editor= sharedPreferences!!.edit()
            editor.putString("firstTimeRun","false")
            editor.apply()

            Handler().postDelayed({
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()
            }, 3000) // 3000 is the delayed time in milliseconds.


        }




    }
}