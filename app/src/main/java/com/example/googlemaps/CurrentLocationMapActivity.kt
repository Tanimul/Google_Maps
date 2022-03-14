package com.example.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.googlemaps.databinding.ActivityCurrentLocationMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CurrentLocationMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityCurrentLocationMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    lateinit var cur_location: Location
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentLocationMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                // Got last known location. In some rare situations this can be null.
                Log.d("ddddddddd", "onCreate: ")
                if (it != null) {
                    cur_location = it
                    Log.d("ddddddddd", "onCreate: $it")
                }
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map_fragment) as? SupportMapFragment
                mapFragment?.getMapAsync(this)
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val currentLocation = LatLng(cur_location.latitude, cur_location.longitude)
        Log.d("ddddddddd", "onMapReady: $currentLocation")
        mMap.addMarker(
            MarkerOptions().position(currentLocation).title("I am here")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 7f))
        mMap.addCircle(
            CircleOptions()
                .center(currentLocation)
                .radius(10000.0)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(70, 150, 50, 50))
        )
    }
}