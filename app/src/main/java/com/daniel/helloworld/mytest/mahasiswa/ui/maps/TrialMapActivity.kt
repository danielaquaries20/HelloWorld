package com.daniel.helloworld.mytest.mahasiswa.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.checkLocationPermission
import com.crocodic.core.helper.LocationHelper
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTrialMapBinding
import com.daniel.helloworld.helper.AddressHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrialMapActivity : NoViewModelActivity<ActivityTrialMapBinding>(R.layout.activity_trial_map) {

    @Inject
    lateinit var addressHelper: AddressHelper

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
            /*Marker
            val latLng = LatLng(-7.1157543, 110.3985217)

            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Markerku")
                    .snippet("Lokasisi lah, pokokmen")
            )

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

            googleMap.isMyLocationEnabled = true*/

            /*Camera Listener
            googleMap.setOnCameraMoveListener {
                binding.ivTarget.alpha = 0.5f
            }

            googleMap.setOnCameraIdleListener {
                binding.ivTarget.alpha = 1f

                val currentLocation = googleMap.cameraPosition.target

                binding.tvLocation.text =
                    "Lat: ${currentLocation.latitude}\nLng: ${currentLocation.longitude}"

                addressHelper.getAddress(
                    LatLng(
                        currentLocation.latitude,
                        currentLocation.longitude
                    )
                ) {
                    binding.tvAddress.text = it
                }
            }*/

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@getMapAsync
            }
            googleMap.isMyLocationEnabled = true

            // -7.1179884, 110.3951523 -> LatLng 1
            // -7.8266, 112.0110 -> LatLng 2

            val area = googleMap.addCircle(
                CircleOptions()
                    .center(LatLng(-7.1179884, 110.3951523))
                    .radius(1_000.0)
                    .strokeColor(Color.parseColor("#FFC80000"))
                    .fillColor(Color.parseColor("#25C80000"))
            )
        }

    }

    private fun isInsideLocation(area: LatLng, position: LatLng): Boolean {
        return LocationHelper.distance(area, position) < 1
    }

    override fun retrieveLocationChange(location: Location) {
        super.retrieveLocationChange(location)

        binding.mapView.getMapAsync { googleMap ->
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location.latitude,
                        location.longitude
                    ), 13f
                )
            )

            val isInside = isInsideLocation(
                LatLng(-7.1179884, 110.3951523),
                LatLng(location.latitude, location.longitude)
            )

            val status = if (isInside) {
                "dalam"
            } else {
                "luar"
            }

            binding.tvStatus.text = "Kamu berada di $status area."
        }

//        myLocation = location
//        Log.d("deviceLocation", "latitude: ${location.latitude}, longitude: ${location.longitude}")
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