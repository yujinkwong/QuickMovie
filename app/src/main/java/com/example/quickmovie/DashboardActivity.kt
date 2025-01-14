package com.example.quickmovie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.viewpager2.widget.ViewPager2
import android.view.View

class DashboardActivity : ComponentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageCarouselAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view to the layout file
        setContentView(R.layout.dashboard)

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

        // Add Menu Button Click Listener
        findViewById<ImageButton>(R.id.menubardashboard).setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // Add click listener for Movie 1
        val movie1Layout = findViewById<View>(R.id.movie1Layout)
        movie1Layout.setOnClickListener {
            val intent = Intent(this, SelectTimeActivity::class.java) // Replace with your target activity
            startActivity(intent)
        }

        // Add click listener for Movie 2
        val movie2Layout = findViewById<View>(R.id.movie2Layout)
        movie2Layout.setOnClickListener {
            val intent = Intent(this, SelectTimeActivity::class.java) // Replace with your target activity
            intent.putExtra("MOVIE_NAME", "SONIC THE HEDGEHOG 3")
            startActivity(intent)
        }

        // Add click listener for Movie 3
        val movie3Layout = findViewById<View>(R.id.movie3Layout)
        movie3Layout.setOnClickListener {
            val intent = Intent(this, SelectTimeActivity::class.java) // Replace with your target activity
            intent.putExtra("MOVIE_NAME", "CRAYON SINCHAN: DINOSAUR DIARY")
            startActivity(intent)
        }

        // Add click listener for Movie 4
        val movie4Layout = findViewById<View>(R.id.movie4Layout)
        movie4Layout.setOnClickListener {
            val intent = Intent(this, SelectTimeActivity::class.java) // Replace with your target activity
            intent.putExtra("MOVIE_NAME", "WICKED")
            startActivity(intent)
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

        // Optional: Pause auto-scroll when the user interacts with the ViewPager
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove any pending callbacks to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
    }
}
