package com.example.tugasbesarpbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.databinding.FragmentLandingPageBinding
import com.example.tugasbesarpbp.entity.Pocket
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class landingPageFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }

    lateinit var tvUsername:TextView

    lateinit var binding: FragmentLandingPageBinding

    private val CHANNEL="channel_notification"
    private val notification1=101

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLandingPageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvUsername= binding.txtUserName

        val username=requireActivity().intent.getStringExtra("usernameLogin")

        tvUsername.text=username

        CoroutineScope(Dispatchers.IO).launch {
            val idUser= requireActivity().intent.getIntExtra("idLogin",0)

            println("tipe id" + idUser::class.simpleName)

            val result= db.pocketDao().getPocket(idUser)
            val adapter: rvPocketAdapter = rvPocketAdapter(result,object : rvPocketAdapter.OnAdapterListener{
                override fun onClick(pocket: pocket) {
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketDetailFragment())
                }
            })
            val layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            val rvPocket: RecyclerView= view.findViewById(R.id.rv_pocket)

            rvPocket.layoutManager=layoutManager

            rvPocket.setHasFixedSize(true)

            rvPocket.adapter=adapter
        }

        CoroutineScope(Dispatchers.IO).launch {
            val idUser= requireActivity().intent.getIntExtra("idLogin",0)

            val result= db.pocketDao().getPocket(idUser)
            println("hasil result" + result)

            if(result.size==0){
                createNotificationChannel()
                sendNotification()

            }

        }

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name="Notification Title"
            val descriptionText="Notification Description"

            val channel= NotificationChannel(CHANNEL,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description=descriptionText
            }

            val notificationManager: NotificationManager =
                requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){

        val title="Terimakasih telah bergabung"
        val desc="Segera memulai dan lihat panduan berikut"
        val bigDesc="Pembuatan pocket dapat dilakukan pada menu pocket lalu tekan 'add pocket'. " +
                "Setelahnya masukkan nama pocket, jumlah pocket, dan tekah add pocket ketika selesai memasukkan input."

        var notification = NotificationCompat.Builder(requireActivity(), CHANNEL)
            .setSmallIcon(R.drawable.ic_baseline_info_24)
            .setContentTitle(title)
            .setContentText(desc)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(bigDesc))
            .build()

        with(NotificationManagerCompat.from(requireContext())){
            notify(notification1,notification)
        }

    }



}