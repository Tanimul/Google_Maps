package com.example.googlemaps

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemaps.databinding.ActivityMainBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CHECK_SETTINGS = 666
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.simpleMapinit.setOnClickListener {
            startActivity(Intent(this, SimpleMapActivity::class.java))
        }

        binding.enableornot.setOnClickListener {
            val isGPSON: Boolean = isLocationEnabled(this)
            if (isGPSON) {
                Toast.makeText(this, "Location On", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location off", Toast.LENGTH_SHORT).show()
            }
        }

        binding.locationEnable.setOnClickListener {
            displayLocationSettingsRequest(this)
        }

        binding.curLocation.setOnClickListener {
            startActivity(Intent(this, CurrentLocationMapActivity::class.java))
        }

        binding.detailsLocation.setOnClickListener {
            startActivity(Intent(this, ShowDetailsMapActivity::class.java))
        }
    }

    private fun displayLocationSettingsRequest(context: Context) {


        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            Toast.makeText(this@MainActivity, "location already Enable", Toast.LENGTH_SHORT).show()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        this@MainActivity,
                        REQUEST_CHECK_SETTINGS
                    )
                    Toast.makeText(
                        this@MainActivity,
                        "location Enable Processing",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }

    fun isLocationEnabled(context: Context): Boolean {
        var isGPSON: Boolean = false
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSON = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isGPSON
    }

}