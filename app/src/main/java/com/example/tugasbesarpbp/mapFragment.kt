package com.example.tugasbesarpbp

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tugasbesarpbp.databinding.FragmentMapBinding
import com.example.tugasbesarpbp.databinding.FragmentPocketBinding
import com.example.tugasbesarpbp.peta.CustomInfoWindow
import com.example.tugasbesarpbp.peta.ModelMain
import kotlinx.android.synthetic.main.fragment_map.*
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.*
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2
import java.io.IOException
import java.nio.charset.StandardCharsets




class mapFragment : Fragment() {
    var modelMainList: MutableList<ModelMain> = ArrayList()
    lateinit var mapController: MapController
    lateinit var overlayItem: ArrayList<OverlayItem>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding= FragmentMapBinding.inflate(inflater, container, false)
        val view= binding!!.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))

        val geoPoint= GeoPoint(-7.78165, 110.414497)
        mapView.setMultiTouchControls(true)
        mapView.controller.animateTo(geoPoint)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        mapController= mapView.controller as MapController
        mapController.setCenter(geoPoint)
        mapController.zoomTo(15)


        //compass
        var compassOverlay= CompassOverlay(context, InternalCompassOrientationProvider(context), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        //rotation gesture
        var rotationGestureOverlay = RotationGestureOverlay(mapView)
        rotationGestureOverlay.isEnabled
        mapView.setMultiTouchControls(true)
        mapView.overlays.add(rotationGestureOverlay)

        //scale bar
        val dm: DisplayMetrics= requireContext().resources.displayMetrics
        val scaleBarOverlay = ScaleBarOverlay(mapView)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10)
        mapView.overlays.add(scaleBarOverlay)

        getLocationMarker()

    }


    private fun getLocationMarker(){
        try{
            val stream= activity?.assets?.open("sample_maps.json")
            val size= stream?.available()
            val buffer= ByteArray(size!!)
            stream?.read(buffer)
            stream?.close()
            val strContent= String(buffer, StandardCharsets.UTF_8)
            try {
                val jsonObject= JSONObject(strContent)
                val jsonArrayResult= jsonObject.getJSONArray("results")
                for(i in 0 until jsonArrayResult.length()){
                    val jsonObjectResult= jsonArrayResult.getJSONObject(i)
                    val modelMain= ModelMain()
                    modelMain.strName= jsonObjectResult.getString("name")
                    modelMain.strVicinity= jsonObjectResult.getString("vicinity")

                    val jsonObjectGeo= jsonObjectResult.getJSONObject("geometry")
                    val jsonObjectLoc= jsonObjectGeo.getJSONObject("location")
                    modelMain.latLoc= jsonObjectLoc.getDouble("lat")
                    modelMain.longLoc= jsonObjectLoc.getDouble("lng")
                    modelMainList.add(modelMain)
                }
                initMarker(modelMainList)
            }catch (e: JSONException){
                e.printStackTrace()
            }
        }catch (ignored: IOException){
        }
    }

    private fun initMarker(modelList: List<ModelMain>){
        for(i in modelList.indices){
            overlayItem= ArrayList()
            overlayItem.add(
                OverlayItem(
                    modelList[i].strName, modelList[i].strVicinity, GeoPoint(
                        modelList[i].latLoc, modelList[i].longLoc
                    )
                )
            )
            val info= ModelMain()
            info.strName= modelList[i].strName
            info.strVicinity= modelList[i].strVicinity

            val marker= Marker(mapView)
            marker.icon= resources.getDrawable(R.drawable.ic_baseline_location_on_24)
            marker.position= GeoPoint(modelList[i].latLoc, modelList[i].longLoc)
            marker.relatedObject= info
            marker.infoWindow= CustomInfoWindow(mapView)
            marker.setOnMarkerClickListener{ item, arg->
                item.showInfoWindow()
                true
            }

            mapView.overlays.add(marker)
            mapView.invalidate()
        }
    }

    public override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        if(mapView!=null){
            mapView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        if(mapView!=null){
            mapView.onPause()
        }
    }


}