package com.example.quickmovie

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class DashboardActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageCarouselAdapter
    private lateinit var locationTextView: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view to the layout file
        setContentView(R.layout.dashboard)

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize ViewPager2
        viewPager = findViewById(R.id.viewPager)

        // Sample images (replace with your drawable resource IDs)
        val images = listOf(
            R.drawable.sonic3,
            R.drawable.venomposter,
            R.drawable.wicked
        )

        // Set up the adapter
        adapter = ImageCarouselAdapter(images)
        viewPager.adapter = adapter

        // Start auto-scroll
        startAutoScroll(images.size)

        // Initialize location TextView
        locationTextView = findViewById(R.id.locationTextView)

        // Fetch and display the user's location
        getLocation()

        // Add Menu Button Click Listener
        findViewById<ImageButton>(R.id.menubardashboard).setOnClickListener {
            // Handle navigation to the menu
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun startAutoScroll(totalItems: Int) {
        val runnable = object : Runnable {
            override fun run() {
                if (currentPosition == totalItems) {
                    currentPosition = 0
                }
                viewPager.setCurrentItem(currentPosition++, true)
                handler.postDelayed(this, 3000) // Change slides every 3 seconds
            }
        }
        handler.post(runnable)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Get last known location
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    displayLocation(location)
                } else {
                    Toast.makeText(this, "Unable to fetch location. Try again.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to get location: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayLocation(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 5)
            if (!addresses.isNullOrEmpty()) {
                val district = addresses[0].subAdminArea
                val locality = addresses[0].locality
                val adminArea = addresses[0].adminArea

                val districtName = district ?: locality ?: adminArea ?: "Unknown District"
                locationTextView.text = "District: $districtName"
            } else {
                locationTextView.text = "District: Unknown"
            }
        } catch (e: Exception) {
            Log.e("DashboardActivity", "Error fetching location: ${e.message}")
            locationTextView.text = "District: Unknown"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
