package com.daniel.helloworld.mytest.mahasiswa.ui.maps

import android.location.Location
import android.os.Bundle
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.checkLocationPermission
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTrialMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrialMapActivity : NoViewModelActivity<ActivityTrialMapBinding>(R.layout.activity_trial_map) {


    private lateinit var myLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)

        /*enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        checkLocationPermission {
            listenLocationChange()
        }

        binding.mapView.getMapAsync { googleMap ->
            val latLng = LatLng(-7.1157543, 110.3985217)

            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Markerku")
                    .snippet("Lokasisi lah, pokokmen")
            )
            // Pindahkan kamera ke marker
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

//            googleMap.isMyLocationEnabled = true
        }

    }

    override fun retrieveLocationChange(location: Location) {
        super.retrieveLocationChange(location)
        myLocation = location
//        binding.root.snacked("latitude: ${location.latitude} longitude: ${location.longitude}")
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}