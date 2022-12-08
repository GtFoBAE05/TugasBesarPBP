package com.example.tugasbesarpbp

import android.annotation.SuppressLint
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
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfDocument
import android.media.Image
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil.setContentView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.tugasbesarpbp.Room.User
import com.example.tugasbesarpbp.Room.UserDB
import com.example.tugasbesarpbp.api.BillApi
import com.example.tugasbesarpbp.api.UsersApi
import com.example.tugasbesarpbp.databinding.FragmentProfileBinding
import com.example.tugasbesarpbp.models.Bill
import com.example.tugasbesarpbp.models.Users
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import org.intellij.lang.annotations.JdkConstants

class profileFragment : Fragment() {

    val db by lazy { UserDB(requireContext()) }

    lateinit var btnLogout:Button
    lateinit var btnUpdate:Button
    lateinit var tvUsername:TextView
    lateinit var tvEmail:TextView
    lateinit var tvDate:TextView
    lateinit var tvPhone:TextView

    lateinit var binding: FragmentProfileBinding

    private var queue: RequestQueue? = null
//    private var binding: ProfileFragmentBinding? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ProfileFragmentBinding.inflate(layoutInflater)
//        val view: View = binding!!.root
//        setContentView(view)
//
//        binding!!.buttonSave.setOnClickListener {
//            val nama = binding!!.editTextName.text.toString()
//            val umur = binding!!.editTextUmur.text.toString()
//            val tlp = binding!!.editTextHP.text.toString()
//            val alamat = binding!!.editTextAlamat.text.toString()
//            val kampus = binding!!.editTextKampus.text.toString()
//
//            try {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    if (nama.isEmpty() && umur.isEmpty() && tlp.isEmpty() && alamat.isEmpty() && kampus.isEmpty()){
//                        FancyToast.makeText(applicationContext,"Semuanya Tidak boleh Kosong" , FancyToast.LENGTH_SHORT).show()
//                    }else {
//                        createPdf(nama, umur, tlp, alamat, kampus)
//                    }
//
//                }
//            } catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    @SuppressLint("ObsoleteSdkInt")
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Throws(
//        FileNotFoundException::class
//    )

//    private fun createPdf(nama: String, umur: String, tlp: String, alamat: String, kampus: String) {
//
//        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
//        val file = File(pdfPath, "pdf_TugasBesarPBP.pdf")
//        FileOutputStream(file)
//
//        //inisaliasi pembuatan PDF
//        val writer = PdfWriter(file)
//        val pdfDocument = PdfDocument(writer)
//        val document = Document(pdfDocument)
//        pdfDocument.defaultPageSize = PageSize.A4
//        document.setMargins(5f, 5f, 5f, 5f)
//        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(R.drawable.bebas)
//
//        //penambahan gambar pada Gambar atas
//        val bitmap = (d as BitmapDrawable?)!!.bitmap
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//        val bitmapData = stream.toByteArray()
//        val imageData = ImageDataFactory.create(bitmapData)
//        val image = Image(imageData)
//        val namapengguna = Paragraph("Identitas Pengguna").setBold().setFontSize(24f)
//            .setTextAlignment(TextAlignment.CENTER)
//        val group = Paragraph(
//            """
//                        Berikut adalah
//                        Nama Pengguna UAJY 2022/2023
//                        """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)
//
//        //proses pembuatan table
//        val width = floatArrayOf(100f, 100f)
//        val table = Table(width)
//        //pengisian table dengan data-data
//        table.setHorizontalAlignment(JdkConstants.HorizontalAlignment.CENTER)
//        table.addCell(Cell().add(Paragraph("Nama Diri")))
//        table.addCell(Cell().add(Paragraph(nama)))
//        table.addCell(Cell().add(Paragraph("Umur")))
//        table.addCell(Cell().add(Paragraph(umur)))
//        table.addCell(Cell().add(Paragraph("No Telepon")))
//        table.addCell(Cell().add(Paragraph(tlp)))
//        table.addCell(Cell().add(Paragraph("Alamat Domisili")))
//        table.addCell(Cell().add(Paragraph(alamat)))
//        table.addCell(Cell().add(Paragraph("Nama Kampus")))
//        table.addCell(Cell().add(Paragraph(kampus)))
//        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//        table.addCell(Cell().add(Paragraph("Tanggal Buat PDF")))
//        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
//        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a")
//        table.addCell(Cell().add(Paragraph("Pukul Pembuatan")))
//        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))
//
//        //pembuatan QR CODE secara generate dengan bantuan IText7
//        val barcodeQRCode = BarcodeQRCode(
//            """
//                                        $nama
//                                        $umur
//                                        $tlp
//                                        $alamat
//                                        $kampus
//                                        ${LocalDate.now().format(dateTimeFormatter)}
//                                        ${LocalTime.now().format(timeFormatter)}
//                                        """.trimIndent())
//        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
//        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)
//
//        document.add(image)
//        document.add(namapengguna)
//        document.add(group)
//        document.add(table)
//        document.add(qrCodeImage)
//
//
//        document.close()
//        Toast.makeText(this, "Pdf Created", Toast.LENGTH_LONG).show()
//    }

    override fun onStart() {
        super.onStart()
        val userId= requireActivity().intent.getIntExtra("idLogin",0)
        getUsersById(userId)
    }

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

        queue= Volley.newRequestQueue(requireContext())

        tvUsername=binding.txtUsernameProfile
        tvEmail=binding.txtEmailProfile
        tvDate=binding.txtDateProfile
        tvPhone=binding.txtPhoneNumber

        val userId= requireActivity().intent.getIntExtra("idLogin",0)

//        CoroutineScope(Dispatchers.IO).launch{
//            println("user id=" + userId)
//            val resultCheckUser: List<User> = db.userDao().getUserById(userId)
//            println("hasil=" + resultCheckUser)
//            tvUsername.setText("Username:" +resultCheckUser[0].username)
//            tvEmail.setText("Email:" +resultCheckUser[0].email)
//            tvDate.setText("Birth:" +resultCheckUser[0].date)
//            tvPhone.setText("Phone number:" +resultCheckUser[0].NoTelp)
//        }

        //getUsersById(userId)


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

    private fun getUsersById(id: Int){
        val stringRequest: StringRequest = object : StringRequest(Method.GET, UsersApi.GET_BY_ID_URL + id, Response.Listener { response ->
            val gson = Gson()
            val users = gson.fromJson(response, Users::class.java)

            tvUsername.setText("Username:" +users.username)
            tvEmail.setText("Email:" +users.email)
            tvDate.setText("Birth:" +users.date)
            tvPhone.setText("Phone number:" +users.noTelp)




        }, Response.ErrorListener { error ->
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val errors= JSONObject(responseBody)

            }catch (e:Exception){}

        })

        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "Application/json"
                return headers
            }
        }

        queue!!.add(stringRequest)
    }
}