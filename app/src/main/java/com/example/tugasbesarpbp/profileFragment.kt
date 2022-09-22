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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class profileFragment : Fragment() {

    val db by lazy { UserDB(requireContext()) }

    lateinit var btnLogout:Button
    lateinit var tvUsername:TextView
    lateinit var tvEmail:TextView
    lateinit var tvDate:TextView
    lateinit var tvPhone:TextView

    var binding: ActivityHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvUsername=view.findViewById(R.id.txtUsernameProfile)
        tvEmail=view.findViewById(R.id.txtEmailProfile)
        tvDate=view.findViewById(R.id.txtDateProfile)
        tvPhone=view.findViewById(R.id.txtPhoneNumber)

        CoroutineScope(Dispatchers.IO).launch{
            val userId= requireActivity().intent.getIntExtra("idLogin",0)
            println("user id=" + userId)
            val resultCheckUser: List<User> = db.userDao().getUserById(userId)
            println("hasil=" + resultCheckUser)
            tvUsername.setText("Username:" +resultCheckUser[0].username)
            tvEmail.setText("Email:" +resultCheckUser[0].email)
            tvDate.setText("Birth:" +resultCheckUser[0].date)
            tvPhone.setText("Phone number:" +resultCheckUser[0].NoTelp)

        }





        btnLogout=view.findViewById(R.id.btnLogoutInProfilePage)
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