package com.example.tugasbesarpbp

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.tugasbesarpbp.databinding.ActivityHomeBinding
import android.content.Intent
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class profileFragment : Fragment() {

    val db by lazy { UserDB(requireContext()) }

    lateinit var btnLogout:Button
    lateinit var btnUpdate:Button
    lateinit var tvUsername:TextView
    lateinit var tvEmail:TextView
    lateinit var tvDate:TextView
    lateinit var tvPhone:TextView

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUsername=binding.txtUsernameProfile
        tvEmail=binding.txtEmailProfile
        tvDate=binding.txtDateProfile
        tvPhone=binding.txtPhoneNumber

        val userId= requireActivity().intent.getIntExtra("idLogin",0)

        CoroutineScope(Dispatchers.IO).launch{
            println("user id=" + userId)
            val resultCheckUser: List<User> = db.userDao().getUserById(userId)
            println("hasil=" + resultCheckUser)
            tvUsername.setText("Username:" +resultCheckUser[0].username)
            tvEmail.setText("Email:" +resultCheckUser[0].email)
            tvDate.setText("Birth:" +resultCheckUser[0].date)
            tvPhone.setText("Phone number:" +resultCheckUser[0].NoTelp)
        }


        btnUpdate=binding.btnUpdateProfile
        btnUpdate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, updateProfileFragment()).commit()
        }

        btnLogout=binding.btnLogoutInProfilePage
        btnLogout.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Are you sure want to exit?").setPositiveButton("YES", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    activity!!.finishAndRemoveTask()
                }
            }).show()
        }
    }
}