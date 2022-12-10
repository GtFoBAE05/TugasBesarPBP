package com.example.tugasbesarpbp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.tugasbesarpbp.databinding.FragmentBillBinding
import com.example.tugasbesarpbp.databinding.FragmentCameraBinding
import kotlinx.android.synthetic.main.fragment_camera.view.*
import kotlin.math.sqrt


class cameraFragment : Fragment() {
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentCameraBinding.inflate(inflater,container,false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            mCamera = Camera.open()
        }catch (e: Exception){
            Log.d("Error","Failed to get Camera" + e.message)
        }

        if (mCamera != null) {
            mCameraView = CameraView(requireContext(), mCamera!!)
            val camera_view = view.FLCamera as FrameLayout
            camera_view.addView(mCameraView)
        }

        @SuppressLint("MissingInflatedID", "LocalSuppress") val imageClose =
            view.imgClose as ImageButton
        imageClose.setOnClickListener { requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, profileFragment()).commit()}

    }







}