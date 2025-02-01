package com.daniel.helloworld.pertemuan12.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.checkLocationPermission
import com.crocodic.core.helper.LocationHelper
import com.daniel.helloworld.R
import com.daniel.helloworld.databinding.ActivityTrialMapBinding
import com.daniel.helloworld.helper.PenolongLokasi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : NoViewModelActivity<ActivityTrialMapBinding>(R.layout.activity_trial_map) {

    @Inject
    lateinit var adrHelper: PenolongLokasi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)

        checkLocationPermission {
            listenLocationChange()
        }
        binding.mapView.getMapAsync { googleMap ->
            /*googleMap.setOnCameraMoveListener {
                binding.ivTarget.alpha = 0.5f
            }

            googleMap.setOnCameraIdleListener {
                binding.ivTarget.alpha = 1f

                val curLocation = googleMap.cameraPosition.target
                binding.tvLocation.text = "Lat: ${curLocation.latitude} \nLng: ${curLocation.longitude}"

                adrHelper.getAddress(LatLng(curLocation.latitude, curLocation.longitude)) {
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

            val area = googleMap.addCircle(CircleOptions()
                .center(LatLng(-7.0644051, 110.4139525))
                .radius(1_000.0)
                .strokeColor(Color.parseColor("#FFC80000"))
                .fillColor(Color.parseColor("#25C80000")))
        }
    }

    private fun isInsideLocation(area: LatLng, position: LatLng) : Boolean {
        return LocationHelper.distance(area, position) < 1
    }

    override fun retrieveLocationChange(location: Location) {
        super.retrieveLocationChange(location)
        Log.d("deviceLocation", "latitude: ${location.latitude}, longitude: ${location.longitude}")

        binding.mapView.getMapAsync {
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 13f))

            val isInside = isInsideLocation(LatLng(-7.0644051, 110.4139525), LatLng(location.latitude, location.longitude))

            val status = if(isInside) "dalam" else "luar"

            binding.tvStatus.text = "Kamu berada di $status area."
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
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
