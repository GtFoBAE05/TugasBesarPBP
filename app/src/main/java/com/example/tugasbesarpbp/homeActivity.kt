package com.example.tugasbesarpbp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class homeActivity : AppCompatActivity() {
    private lateinit var botNavBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        botNavBar = findViewById(R.id.bottomnav)



        fun changeFragment(fragment: Fragment?) {
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment).commit()
            }
        }

        fun onCreateOptionsMenu(menu: Menu): Boolean {
            val menuInflater = MenuInflater(this)
            menuInflater.inflate(R.menu.home_menu, menu)
            return true
        }

        if(botNavBar.selectedItemId==R.id.menu_home){
            changeFragment(landingPageFragment())
        }else if(botNavBar.selectedItemId==R.id.menu_pocket){
            changeFragment(pocketFragment())
        }else{
            changeFragment(profileFragment())
        }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if(item.itemId == R.id.menu_home){
//            changeFragment(landingPageFragment())
//        }else if(item.itemId==R.id.menu_pocket){
//            changeFragment(pocketFragment())
//        }else{
//            changeFragment(profileFragment())
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
    }
}