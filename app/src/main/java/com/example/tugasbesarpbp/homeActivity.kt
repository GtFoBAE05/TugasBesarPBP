package com.example.tugasbesarpbp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.tugasbesarpbp.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class homeActivity : AppCompatActivity() {
    private lateinit var botNavBar: BottomNavigationView

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        botNavBar = binding.bottomnav
        changeFragment(landingPageFragment())

        botNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home->changeFragment(landingPageFragment())
                R.id.menu_pocket->changeFragment(pocketFragment())
                R.id.menu_profile->{
                    changeFragment(profileFragment())
                }
            }
            true
        }
    }

        fun changeFragment(fragment: Fragment?) {
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment).commit()
            }
        }
}