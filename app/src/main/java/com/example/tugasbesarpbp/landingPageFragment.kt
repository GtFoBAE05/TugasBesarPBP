package com.example.tugasbesarpbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.api.PocketApi
import com.example.tugasbesarpbp.databinding.FragmentLandingPageBinding
import com.example.tugasbesarpbp.models.Pocket
import com.example.tugasbesarpbp.pocketRoom.PocketDB
import com.example.tugasbesarpbp.pocketRoom.pocket
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class landingPageFragment : Fragment() {
    val db by lazy { PocketDB(requireContext()) }

    lateinit var tvUsername:TextView

    lateinit var binding: FragmentLandingPageBinding

    private val CHANNEL="channel_notification"
    private val notification1=101

    private var queue: RequestQueue? = null
    var adapter: rvPocketAdapter?=null

    override fun onStart() {
        super.onStart()
        getAllPocket()
    }

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
        queue= Volley.newRequestQueue(requireContext())
        tvUsername= binding.txtUserName

        val username=requireActivity().intent.getStringExtra("usernameLogin")

        tvUsername.text=username

        adapter= rvPocketAdapter(ArrayList(),object : rvPocketAdapter.OnAdapterListener{
            override fun onClick(pocket: Pocket) {
                    requireActivity().intent.putExtra("pocketId", pocket.id)
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketDetailFragment()).commit()
                }
            })
        val layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val rvPocket: RecyclerView= view.findViewById(R.id.rv_pocket)

        rvPocket.layoutManager=layoutManager

        rvPocket.setHasFixedSize(true)

        rvPocket.adapter=adapter

//        CoroutineScope(Dispatchers.IO).launch {
//            val idUser= requireActivity().intent.getIntExtra("idLogin",0)
//
//
//            val result= db.pocketDao().getPocket(idUser)
//            val adapter: rvPocketAdapter = rvPocketAdapter(result,object : rvPocketAdapter.OnAdapterListener{
//                override fun onClick(pocket: pocket) {
//                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, pocketDetailFragment())
//                }
//            })
//            val layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//            val rvPocket: RecyclerView= view.findViewById(R.id.rv_pocket)
//
//            rvPocket.layoutManager=layoutManager
//
//            rvPocket.setHasFixedSize(true)
//
//            rvPocket.adapter=adapter
//        }

//        CoroutineScope(Dispatchers.IO).launch {
//            val idUser= requireActivity().intent.getIntExtra("idLogin",0)
//
//            val result= db.pocketDao().getPocket(idUser)
//            println("hasil result" + result)
//
//            if(result.size==0){
//                createNotificationChannel()
//                sendNotification()
//
//            }
//
//        }

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

    fun getAllPocket(){
        val stringRequest: StringRequest = object : StringRequest(Method.GET, PocketApi.GET_ALL_URL, Response.Listener { response ->
            val gson= Gson()
            val pocket: Array<Pocket> = gson.fromJson(response, Array<Pocket>::class.java)

            val id = requireActivity().intent.getIntExtra("idLogin",0)
            adapter!!.setList(id, pocket)

        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)

            }catch (e:Exception){}

        })
        {
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