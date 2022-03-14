package com.example.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.googlemaps.databinding.ActivityShowDetailsMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import java.io.IOException
import java.util.*

class ShowDetailsMapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowDetailsMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShowDetailsMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.button1.setOnClickListener {

            getlocation()
        }

    }
    private fun getlocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.getLastLocation()
            .addOnCompleteListener(OnCompleteListener<Location?> { task ->
                val location = task.result
                if (location != null) {
                    try {
                        val geocoder =
                            Geocoder(this, Locale.getDefault())
                        val addressList =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        binding.textView1.text = Html.fromHtml(
                            "<font color='#6200EE'><b>Latitude : </b><br></font>"
                                    + addressList[0].latitude
                        )
                        binding.textView2.text = Html.fromHtml(
                            "<font color='#6200EE'><b>Longitude : </b><br></font>"
                                    + addressList[0].longitude
                        )
                        binding.textView3.text = Html.fromHtml(
                            "<font color='#6200EE'><b>Country name : </b><br></font>"
                                    + addressList[0].countryName
                        )
                        binding.textView4.text = Html.fromHtml(
                            "<font color='#6200EE'><b>Locality : </b><br></font>"
                                    + addressList[0].locality
                        )
                        binding.textView5.text = Html.fromHtml(
                            "<font color='#6200EE'><b>Address : </b><br></font>"
                                    + addressList[0].getAddressLine(0)
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            })
    }
}