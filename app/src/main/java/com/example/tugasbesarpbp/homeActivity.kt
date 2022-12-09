package com.example.tugasbesarpbp

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tugasbesarpbp.databinding.ActivityHomeBinding
import com.github.venom.Venom
import com.github.venom.service.NotificationConfig
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
                //R.id.menu_map->changeFragment(mapFragment())
                R.id.menu_bill->changeFragment(billFragment())
                R.id.menu_reminder->changeFragment(reminderFragment())
            }
            true
        }

        val venom = Venom.createInstance(this)

        val notification = NotificationConfig.Builder(this)
            .buttonCancel(com.github.venom.R.string.venom_notification_button_cancel)
            .buttonKill(getString(com.github.venom.R.string.venom_notification_button_kill))
            .build()

        venom.initialize(notification)
        venom.start()



    }


        fun changeFragment(fragment: Fragment?) {
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment).commit()
            }
        }
}