package com.example.googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlemaps.databinding.ActivitySimpleMapBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SimpleMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivitySimpleMapBinding
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val primeBazaar = LatLng(23.6960147, 90.5004324)
        mMap.addMarker(
            MarkerOptions().position(primeBazaar).title("Prime Deal and Bazaar")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(primeBazaar, 7f))
    }
}